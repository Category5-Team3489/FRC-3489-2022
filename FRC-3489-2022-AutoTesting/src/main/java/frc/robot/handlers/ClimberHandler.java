package frc.robot.handlers;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.Constants;
import frc.robot.framework.RobotHandler;
import frc.robot.types.ClimberStep;

public class ClimberHandler extends RobotHandler{

    private boolean isClimbing = false;

    public boolean isClimbing() {
        return isClimbing;
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
        components.brakeSolenoid.set(value);
    }
    private void setTelescope(double speed) {
        components.telescopeMotor.set(speed);
    }

    private void resetTelecopeEncoders() {
        components.telescopeMotor.setSelectedSensorPosition(0);
    }
    private double getTelecopeEncoderPosition() {
        return Math.abs(components.telescopeMotor.getSelectedSensorPosition());
    }

    private ClimberStep climberStep = ClimberStep.S0Default;
    private Timer timer = new Timer();
    private boolean stepInitialized = false;

    private boolean climbButton = false;
    private boolean climbActivateButton = false;
    private boolean climbResetButton = false;
    private boolean climbEStopButton = false;

    private void setStep(ClimberStep step) {
        climberStep = step;
        shuffleboardHandler.setString(true, "Climber Step", step.toString());
    }

    private void nextStep() {
        timer.reset();
        stepInitialized = false;
        int step = climberStep.ordinal();
        int last = ClimberStep.values().length - 1;
        step++;
        if (step >= last) {
            setStep(ClimberStep.S0Default);
        }
        else {
            setStep(ClimberStep.values()[step]);
        }
    }

    private boolean shouldClimb() {
        return climbButton && climbActivateButton;
    }

    private boolean shouldReset() {
        return climbResetButton && climbActivateButton;
    }

    private boolean shouldEStop() {
        return climbEStopButton && climbActivateButton;
    }

    @Override
    public void teleopPeriodic() {
        climbButton = components.manipulatorJoystick.getRawButton(Constants.ButtonClimb);
        climbActivateButton = components.manipulatorJoystick.getRawButton(Constants.ButtonClimbActivate);
        climbResetButton = components.manipulatorJoystick.getRawButton(Constants.ButtonClimbReset);
        climbEStopButton = components.manipulatorJoystick.getRawButton(Constants.ButtonClimbEStop);

        if (shouldReset())
            setStep(ClimberStep.S0Default);

        if (shouldEStop())
            setStep(ClimberStep.S11EStop);

        switch (climberStep) {
            case S0Default:
                S0Default();
                break;
            case S1UnbreakAndExtendLower:
                S1UnbreakAndExtendLower();
                break;
            case S2ExtendTelesope:
                S2ExtendTelesope();
                break;
            case S3DriveToMidBar:
                S3DriveToMidBar();
                break;
            case S4RetractTelescope:
                S4RetractTelescope();
                break;
            case S5Brake:
                S5Brake();
                break;
            case S6FinishedMidBarClimb:
                S6FinishedMidBarClimb();
                break;
            case S7ExtendUpper:
                S7ExtendUpper();
                break;
            case S8ExtendTelesopeSlightly:
                S8ExtendTelesopeSlightly();
                break;
            case S9Unhook:
                S9Unhook();
                break;
            case S10DefaultAndDisabled:
                S10DefaultAndDisabled();
                break;
            case S11EStop:
                S11EStop();
                break;
            default:
                break;
        }
    }

    private void S0Default() {
        if (!stepInitialized) {
            stepInitialized = true;
            setBrake(false);
            setLower(false);
            setUpper(false);
            setHooks(false);
        }
        if (shouldClimb())
            nextStep();
    }
    private void S1UnbreakAndExtendLower() {
        if (!stepInitialized) {
            stepInitialized = true;
            setBrake(true);
            setLower(true);
        }
        if (timer.hasElapsed(Constants.S1TimeDelay))
            nextStep();
    }
    private void S2ExtendTelesope() {
        if (!stepInitialized) {
            stepInitialized = true;
            resetTelecopeEncoders();
        }
        if (getTelecopeEncoderPosition() < Constants.ClicksExtendTelesope) {
            setTelescope(Constants.TelescopeExtendSpeed);
        }
        else {
            setTelescope(0);
            nextStep();
        }
    }
    private void S3DriveToMidBar() {
        if (!stepInitialized) {
            stepInitialized = true;
            components.leftFrontDriveMotor.setSelectedSensorPosition(0);
        }
        double driveEncoderPosition = Math.abs(components.leftFrontDriveMotor.getSelectedSensorPosition());
        if (driveEncoderPosition < Constants.ClicksToDriveToMidBar) {
            components.drive.tankDrive(Constants.DriveToMidBarSpeed, Constants.DriveToMidBarSpeed);
        }
        else {
            components.drive.stopMotor();
            nextStep();
        }
    }
    private void S4RetractTelescope() {
        if (!stepInitialized) {
            stepInitialized = true;
            resetTelecopeEncoders();
        }
        if (getTelecopeEncoderPosition() < Constants.ClicksRetractTelesope) {
            setTelescope(Constants.TelescopeRetractSpeed);
        }
        else {
            setTelescope(0);
            nextStep();
        }
    }
    private void S5Brake() {
        if (!stepInitialized) {
            stepInitialized = true;
            setBrake(false);
        }
        nextStep();
    }
    private void S6FinishedMidBarClimb() {
        if (shouldClimb())
            nextStep();
    }
    private void S7ExtendUpper() {
        if (!stepInitialized) {
            stepInitialized = true;
            setUpper(true);
        }
        if (timer.hasElapsed(Constants.S7TimeDelay))
            nextStep();
    }
    private void S8ExtendTelesopeSlightly() {
        if (!stepInitialized) {
            stepInitialized = true;
            resetTelecopeEncoders();
        }
        if (getTelecopeEncoderPosition() < Constants.ClicksExtendTelescopeSlightly) {
            setTelescope(Constants.TelescopeExtendSpeed);
        }
        else {
            setTelescope(0);
            nextStep();
        }
    }
    private void S9Unhook() {
        if (!stepInitialized) {
            stepInitialized = true;
            setHooks(true);
        }
        if (timer.hasElapsed(Constants.S9TimeDelay))
            nextStep();
    }
    private void S10DefaultAndDisabled() {
        if (!stepInitialized) {
            stepInitialized = true;
            setBrake(false);
            setLower(false);
            setUpper(false);
            setHooks(false);
        }
    }
    private void S11EStop() {
        if (!stepInitialized) {
            stepInitialized = true;
            setTelescope(0);
            setBrake(false);
        }
    }
}
