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
        components.hookSolenoid.set(!value);
    }
    private void setBrake(boolean value) {
        components.brakeSolenoid.set(!value);
    }
    private void setTelescope(double speed) {
        components.telescopeMotor.set(speed); // Positive up
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
    private boolean climbHighButton = false;
    private boolean climbActivateButton = false;
    private boolean climbResetButton = false;
    private boolean climbEStopButton = false;

    private boolean shouldInit() {
        if (stepInitialized)
            return false;
        stepInitialized = true;
        return true;
    }

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
    }

    @Override
    public void teleopPeriodic() {
        climbButton = components.manipulatorJoystick.getRawButton(Constants.ButtonClimb);
        climbHighButton = components.manipulatorJoystick.getRawButton(Constants.ButtonClimbHigh);
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
            case S1ExtendLower:
                S1ExtendLower();
                break;
            case S2ExtendTelesope:
                S2ExtendTelesope();
                break;
            case S3DriveToMidBarThenRetractTelescope:
                S3DriveToMidBarThenRetractTelescope();
                break;
            case S4FinishedMidBarClimb:
                S4FinishedMidBarClimb();
                break;
            case S5ExtendUpper:
                S5ExtendUpper();
                break;
            case S6ExtendTelesopeSlightly:
                S6ExtendTelesopeSlightly();
                break;
            case S7UnhookAndRetractTelescope:
                S7UnhookAndRetractTelescope();
                break;
            case S8DefaultAndDisabled:
                S8DefaultAndDisabled();
                break;
            case S10EStop:
                S9EStop();
                break;
            default:
                break;
        }
    }

    // TODO safties for anything with encoders

    private void S0Default() {
        if (shouldInit()) {
            setBrake(true);
            setLower(false);
            setUpper(false);
            setHooks(true);
        }
        if (shouldClimb())
            nextStep();
        // TODO May not want, but could help recover if you misclick
        if (shouldClimbHigh())
            setStep(ClimberStep.S7ExtendUpper);
    }
    private void S1ExtendLower() {
        if (shouldInit()) {
            setBrake(false);
            setLower(true);
        }
        if (timer.hasElapsed(Constants.S1TimeDelay))
            nextStep();
    }
    private void S2ExtendTelesope() {
        if (shouldInit()) {
            resetTelecopeEncoders();
        }
        if (getTelecopeEncoderPosition() < Constants.ClicksExtendTelesope) {
            setTelescope(Constants.TelescopeExtendSpeed);
        }
        else {
            setBrake(false);
            setTelescope(0);
            nextStep();
        }
    }
    private void S3DriveToMidBarThenRetractTelescope() {
        if (shouldInit()) {
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
        if (shouldInit()) {
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
    private void S4FinishedMidBarClimb() {
        if (shouldClimbHigh())
            nextStep();
    }
    private void S5ExtendUpper() {
        if (shouldInit()) {
            setUpper(true);
        }
        if (timer.hasElapsed(Constants.S7TimeDelay))
            nextStep();
    }
    private void S6ExtendTelesopeSlightly() {
        if (shouldInit()) {
            resetTelecopeEncoders();
            setBrake(true);
        }
        if (getTelecopeEncoderPosition() < Constants.ClicksExtendTelescopeSlightly) {
            setTelescope(Constants.TelescopeExtendSpeed);
        }
        else {
            setTelescope(0);
            setBrake(false);
            nextStep();
        }
    }
    private void S7UnhookAndRetractTelescope() {
        if (shouldInit()) {
            resetTelecopeEncoders();
            setBrake(true);
            setHooks(true);
        }
        if (getTelecopeEncoderPosition() < Constants.ClicksRetractTelescopeMajorly) {
            setTelescope(Constants.TelescopeRetractSpeed);
        }
        else {
            setTelescope(0);
            setBrake(false);
            if (timer.hasElapsed(Constants.S9TimeDelay))
                nextStep();
        }
    }
    private void S8DefaultAndDisabled() {
        if (shouldInit()) {
            setBrake(false);
            setLower(false);
            setUpper(false);
            setHooks(false);
        }
    }
    private void S9EStop() {
        if (shouldInit()) {
            components.drive.stopMotor();
            setTelescope(0);
            setBrake(true);
        }
    }
}
