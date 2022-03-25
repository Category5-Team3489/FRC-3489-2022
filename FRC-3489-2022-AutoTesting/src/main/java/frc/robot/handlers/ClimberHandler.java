package frc.robot.handlers;

import frc.robot.Constants;
import frc.robot.auto.framework.AutoBuilder;
import frc.robot.auto.framework.AutoInstruction;
import frc.robot.framework.RobotHandler;
import frc.robot.interfaces.IShuffleboardState;
import frc.robot.types.ClimberStep;

public class ClimberHandler extends RobotHandler implements IShuffleboardState {

    private double telescopeEncoderOffset = 0;

    private ClimberStep climberStep = ClimberStep.Default;
    private boolean stepInitialized = false;
    private boolean climbingToHighBar = false;

    private boolean climbMidButton = false;
    private boolean climbHighButton = false;
    private boolean climbActivateButton = false;
    private boolean climbResetButton = false;
    private boolean climbEStopButton = false;

    public boolean isClimbing() {
        return climberStep != ClimberStep.Default;
    }

    public void setShuffleboardState() {
        shuffleboardHandler.setString(true, "Climber Step", climberStep.toString());
    }

    private void resetTelecopeEncoders() {
        telescopeEncoderOffset = components.telescopeMotor.getSelectedSensorPosition();
    }
    private double getTelecopeEncoderPosition() {
        return Math.abs(components.telescopeMotor.getSelectedSensorPosition() - telescopeEncoderOffset);
    }

    private void setLower(boolean value) {
        components.lowerSolenoid.set(value);
    }
    private void setUpper(boolean value) {
        components.upperSolenoid.set(value);
    }
    private void setHooks(boolean value) {
        components.hookSolenoid.set(value);
    }
    private void setBrake(boolean value) {
        components.brakeSolenoid.set(!value);
    }
    private void setTelescope(double speed) {
        components.telescopeMotor.set(-speed); // Positive up
    }

    private boolean shouldInit() {
        if (stepInitialized)
            return false;
        stepInitialized = true;
        return true;
    }

    private void setStep(ClimberStep step) {
        climberStep = step;
        stepInitialized = false;
        setShuffleboardState();
    }

    private void nextStep() {
        int step = climberStep.ordinal();
        int last = ClimberStep.values().length - 1;
        step++;
        if (step >= last) {
            setStep(ClimberStep.Default);
        }
        else {
            setStep(ClimberStep.values()[step]);
        }
    }

    private boolean shouldClimbMid() {
        return climbMidButton && climbActivateButton;
    }

    private boolean shouldClimbHigh() {
        return climbHighButton && climbActivateButton;
    }

    private boolean shouldReset() {
        return climbResetButton && climbActivateButton;
    }

    private boolean shouldEStop() {
        return climbEStopButton && climbActivateButton;
    }

    @Override
    public void teleopInit() {
        resetTelecopeEncoders();
    }

    private boolean underManualControl = false;

    @Override
    public void teleopPeriodic() {

        climbMidButton = components.manipulatorJoystick.getRawButton(Constants.ButtonClimbMid);
        climbHighButton = components.manipulatorJoystick.getRawButton(Constants.ButtonClimbHigh);
        climbActivateButton = components.manipulatorJoystick.getRawButton(Constants.ButtonClimbActivate);
        climbResetButton = components.manipulatorJoystick.getRawButton(Constants.ButtonClimbReset);
        climbEStopButton = components.manipulatorJoystick.getRawButton(Constants.ButtonClimbEStop);

        if (climbActivateButton) {
            if (components.rightDriveJoystick.getPOV() == 0) {
                underManualControl = true;
                setBrake(false);
                setTelescope(Constants.TelescopeExtendSpeed);
            }
            else if (components.rightDriveJoystick.getPOV() == 180) {
                underManualControl = true;
                setBrake(false);
                setTelescope(Constants.TelescopeRetractSpeed);
            }
        }

        if (underManualControl && components.rightDriveJoystick.getPOV() == -1) {
            underManualControl = false;
            setBrake(true);
            setTelescope(0);
        }

        if (underManualControl)
            return;

        if (shouldReset())
            setStep(ClimberStep.Default);

        if (shouldEStop())
            setStep(ClimberStep.EStop);

        switch (climberStep) {
            case Default:
                if (shouldInit()) {
                    execute(disabled());
                }
                if (shouldClimbMid()) {
                    climbingToHighBar = false;
                    setStep(ClimberStep.ExtendTelescope);
                }
                if (shouldClimbHigh()) {
                    climbingToHighBar = true;
                    setStep(ClimberStep.ExtendLower);
                }
                break;
            case ExtendLower:
                if (shouldInit()) {
                    execute(extendLower());
                }
                break;
            case ExtendTelescope:
                if (shouldInit()) {
                    execute(extendTelescope());
                }
                break;
            case DriveToMidBar:
                if (shouldInit()) {
                    execute(driveToMidBar());
                }
                break;
            case RetractTelescopeOnMidBar:
                if (shouldInit()) {
                    execute(retractTelescopeOnMidBar());
                }
                break;
            case FinishedMidBarClimb:
                if (climbingToHighBar) {
                    if (shouldClimbHigh()) {
                        nextStep();
                    }
                }
                else {
                    setStep(ClimberStep.Disabled);
                }
                break;
            case ExtendUpper:
                if (shouldInit()) {
                    execute(extendUpper());
                }
                break;
            case Unhook:
                if (shouldInit()) {
                    execute(unhook());
                }
                break;
            case Disabled:
                if (shouldInit()) {
                    execute(disabled());
                }
                break;
            case EStop:
                if (shouldInit()) {
                    execute(emergencyStop());
                }
                break;
            default:
                break;
        }
    }

    private AutoInstruction extendLower() {
        AutoInstruction instruction = AutoBuilder.blank(false)
        .onInitialized(() -> {
            setLower(true);
        })
        .onCompleted(() -> nextStep())
        .withTimeout(1);
        return instruction;
    }

    private AutoInstruction extendTelescope() {
        AutoInstruction instruction = AutoBuilder.blank(false)
        .onInitialized(() -> {
            setBrake(false);
            setTelescope(Constants.TelescopeExtendSpeed);
            resetTelecopeEncoders();
        })
        .periodically(() -> {
            return getTelecopeEncoderPosition() > Constants.Climber.ExtendTelesopeClicks ||
            components.telescopeMotor.getStatorCurrent() > Constants.Climber.TelescopeMotorCurrentSafety;
        })
        .onCompleted(() -> {
            setTelescope(0);
            setBrake(true);
            nextStep();
        })
        .withTimeout(2);
        return instruction;
    }

    private AutoInstruction driveToMidBar() {
        AutoInstruction instruction = AutoBuilder.blank(false)
        .onInitialized(() -> {
            components.navx.reset();
            components.drive.tankDrive(Constants.DriveToMidBarSpeed, Constants.DriveToMidBarSpeed);
        })
        .periodically(() -> {
            return Math.abs(components.navx.getPitch()) > Constants.Climber.PitchThreshold;
        })
        .onCompleted(() -> {
            components.drive.stopMotor();
            nextStep();
        })
        .withTimeout(2);
        return instruction;
    }

    private AutoInstruction retractTelescopeOnMidBar() {
        AutoInstruction instruction = AutoBuilder.blank(false)
        .onInitialized(() -> {
            setBrake(false);
            setTelescope(Constants.TelescopeRetractSpeed);
            resetTelecopeEncoders();
        })
        .periodically(() -> {
            return getTelecopeEncoderPosition() > Constants.Climber.RetractTelesopeClicks ||
            components.telescopeMotor.getStatorCurrent() > Constants.Climber.TelescopeMotorCurrentSafety;
        })
        .onCompleted(() -> {
            setTelescope(0);
            setBrake(true);
            nextStep();
        })
        .withTimeout(2);
        return instruction;
    }

    private AutoInstruction extendUpper() {
        AutoInstruction instruction = AutoBuilder.blank(false)
        .onInitialized(() -> {
            setUpper(true);
        })
        .onCompleted(() -> nextStep())
        .withTimeout(1);
        return instruction;
    }

    private AutoInstruction unhook() {
        AutoInstruction instruction = AutoBuilder.blank(false)
        .onInitialized(() -> {
            setHooks(true);
        })
        .onCompleted(() -> nextStep())
        .withTimeout(3);
        return instruction;
    }

    private AutoInstruction disabled() {
        AutoInstruction instruction = AutoBuilder.blank(false)
        .onInitialized(() -> {
            components.drive.stopMotor();
            setTelescope(0);
            setBrake(true);
            setLower(false);
            setUpper(false);
            setHooks(false);
        });
        return instruction;
    }

    private AutoInstruction emergencyStop() {
        AutoInstruction instruction = AutoBuilder.blank(false)
        .onInitialized(() -> {
            components.drive.stopMotor();
            setTelescope(0);
            setBrake(true);
        });
        return instruction;
    }

    private void execute(AutoInstruction instruction) {
        autoHandler.runner.beginExecution(instruction);
    }
}
