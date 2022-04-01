package frc.robot.handlers;

import edu.wpi.first.wpilibj.Timer;
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
    private Timer timer = new Timer();

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
        timer.reset();
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
        timer.start();
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
                    disable();
                }
                if (shouldClimbMid()) {
                    climbingToHighBar = false;
                    nextStep();
                }
                if (shouldClimbHigh()) {
                    climbingToHighBar = true;
                    nextStep();
                }
                break;
            case ExtendLower:
                if (shouldInit()) {
                    setLower(true);
                }
                if (timer.hasElapsed(1))
                    nextStep();
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
                    setUpper(true);
                }
                if (timer.hasElapsed(2))
                    nextStep();
                break;
            case ConfirmUnhook:
                if (shouldClimbHigh()) {
                    nextStep();
                }
                break;
            case Unhook:
                if (shouldInit()) {
                    setHooks(true);
                }
                if (timer.hasElapsed(1.5))
                    nextStep();
                break;
            case Disabled:
                if (shouldInit()) {
                    disable();
                }
                break;
            case EStop:
                if (shouldInit()) {
                    components.drive.stopMotor();
                    setTelescope(0);
                    setBrake(true);
                }
                break;
            default:
                break;
        }
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

    private Timer squareOnMidBarTimer = new Timer();

    private double navxPitchOffset = 0;

    private AutoInstruction driveToMidBar() {
        /*
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
        .withTimeout(4);
        return instruction;
        */
        AutoInstruction instruction = AutoBuilder.blank(false);
        instruction.onInitialized(() -> {
            navxPitchOffset = components.navx.getPitch();
            squareOnMidBarTimer.reset();
            squareOnMidBarTimer.start();
        })
        .periodically(() -> {
            /*
            if (Math.abs(components.navx.getPitch()) < Constants.Climber.PitchThreshold) {
                double speed = GeneralUtils.lerp(Constants.Climber.DriveToMidBarSpeedA, Constants.Climber.DriveToMidBarSpeedB, instruction.timer.get() / 1d);
                components.drive.tankDrive(speed, speed);
            }
            return false;
            */
            /*
            if (!instruction.timer.hasElapsed(Constants.Climber.DriveToMidBarTime)) {
                double speed = GeneralUtils.lerp(Constants.Climber.DriveToMidBarSpeedA, Constants.Climber.DriveToMidBarSpeedB, instruction.timer.get() / Constants.Climber.DriveToMidBarTime);
                components.drive.tankDrive(speed, speed);
            }
            */
            components.drive.tankDrive(Constants.Climber.SquareOnMidBarSpeed, Constants.Climber.SquareOnMidBarSpeed);
            //return Math.abs(components.navx.getPitch() - navxPitchOffset) > Constants.Climber.PitchThreshold;
            return false;
            //return false;
        })
        .onCompleted(() -> {
            components.drive.stopMotor();
            nextStep();
        })
        .withTimeout(3.25);
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

    private void disable() {
        components.drive.stopMotor();
        setTelescope(0);
        setBrake(true);
        setLower(false);
        setUpper(false);
        setHooks(false);
    }

    private void execute(AutoInstruction instruction) {
        autoHandler.runner.beginExecution(instruction);
    }
}
