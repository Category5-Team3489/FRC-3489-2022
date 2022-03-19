package frc.robot.handlers;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.Constants;
import frc.robot.framework.RobotHandler;
import frc.robot.types.ClimberStep;

public class ClimberHandler extends RobotHandler{

    public boolean isClimbing() {
        return climberStep != ClimberStep.S0Default;
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

    private void resetTelecopeEncoders() {
        telescopeEncoderOffset = components.telescopeMotor.getSelectedSensorPosition();
    }
    private double getTelecopeEncoderPosition() {
        return Math.abs(components.telescopeMotor.getSelectedSensorPosition() - telescopeEncoderOffset);
    }

    private ClimberStep climberStep = ClimberStep.S0Default;
    private Timer timer = new Timer();
    private boolean stepInitialized = false;

    private boolean climbButton = false;
    private boolean climbHighButton = false;
    private boolean climbActivateButton = false;
    private boolean climbResetButton = false;
    private boolean climbEStopButton = false;

    private double telescopeEncoderOffset = 0;

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
        shuffleboardHandler.setString(true, "Climber Step", step.toString());
    }

    private void nextStep() {
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
        resetTelecopeEncoders();
    }

    private boolean wasControllingTelescopeManually = false;

    @Override
    public void teleopPeriodic() {
        climbButton = components.manipulatorJoystick.getRawButton(Constants.ButtonClimb);
        climbHighButton = components.manipulatorJoystick.getRawButton(Constants.ButtonClimbHigh);
        climbActivateButton = components.manipulatorJoystick.getRawButton(Constants.ButtonClimbActivate);
        climbResetButton = components.manipulatorJoystick.getRawButton(Constants.ButtonClimbReset);
        climbEStopButton = components.manipulatorJoystick.getRawButton(Constants.ButtonClimbEStop);

        if (climbActivateButton) {
            if (components.rightDriveJoystick.getPOV() == 0) {
                wasControllingTelescopeManually = true;
                setBrake(false);
                setTelescope(Constants.TelescopeExtendSpeed);
            }
            else if (components.rightDriveJoystick.getPOV() == 180) {
                wasControllingTelescopeManually = true;
                setBrake(false);
                setTelescope(Constants.TelescopeRetractSpeed);
            }
        }

        if (wasControllingTelescopeManually && components.rightDriveJoystick.getPOV() == -1) {
            wasControllingTelescopeManually = false;
            setBrake(true);
            setTelescope(0);
        }

        if (wasControllingTelescopeManually)
            return;

        if (shouldReset())
            setStep(ClimberStep.S0Default);

        if (shouldEStop())
            setStep(ClimberStep.S10EStop);

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
            case S7ConfirmUnhook:
                S7ConfirmUnhook();
                break;
            case S8UnhookAndRetractTelescope:
                S8UnhookAndRetractTelescope();
                break;
            case S9DefaultAndDisabled:
                S9DefaultAndDisabled();
                break;
            case S10EStop:
                S10EStop();
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
            setHooks(false);
        }
        if (shouldClimb())
            nextStep();
        if (shouldClimbHigh())
            setStep(ClimberStep.S5ExtendUpper);
    }
    private void S1ExtendLower() {
        if (shouldInit()) {
            setLower(true);
        }
        if (timer.hasElapsed(Constants.Climber.S1TimeDelay))
            nextStep();
    }
    private void S2ExtendTelesope() {
        if (shouldInit()) {
            setBrake(false);
            resetTelecopeEncoders();
        }
        if (getTelecopeEncoderPosition() < Constants.Climber.ExtendTelesopeClicks) {
            setTelescope(Constants.TelescopeExtendSpeed);
        }
        else {
            setBrake(true);
            setTelescope(0);
            nextStep();
        }
        if (timer.hasElapsed(Constants.Climber.S2SafetyTimeout)) {
            setBrake(true);
            setTelescope(0);
            nextStep();
        }
    }
    boolean s3StartRetract = false;
    private void S3DriveToMidBarThenRetractTelescope() {
        if (shouldInit()) {
            components.leftFrontDriveMotor.setSelectedSensorPosition(0);
            resetTelecopeEncoders();
            components.navx.reset();
            s3StartRetract = false;
        }
        components.drive.tankDrive(Constants.DriveToMidBarSpeed, Constants.DriveToMidBarSpeed);
        if (Math.abs(components.navx.getPitch()) > Constants.ClimberPitchThreshold ||
        timer.hasElapsed(Constants.Climber.S3DriveSafetyTimeout)) {
            s3StartRetract = true;
            timer.reset();
        }
        if (s3StartRetract) {
            if (getTelecopeEncoderPosition() < Constants.Climber.RetractTelesopeClicks) {
                setBrake(false);
                setTelescope(Constants.TelescopeRetractSpeed);
            }
            else {
                components.drive.stopMotor();
                setTelescope(0);
                setBrake(true);
                nextStep();
            }
            if (timer.hasElapsed(Constants.Climber.S3RetractTimeout)) {
                components.drive.stopMotor();
                setTelescope(0);
                setBrake(true);
                nextStep();
            }
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
        if (timer.hasElapsed(Constants.Climber.S5TimeDelay))
            nextStep();
    }
    private void S6ExtendTelesopeSlightly() {
        nextStep();
        return;
        /*
        if (shouldInit()) {
            resetTelecopeEncoders();
            setBrake(false);
        }
        if (getTelecopeEncoderPosition() < Constants.ClicksExtendTelescopeSlightly) {
            setTelescope(Constants.TelescopeExtendSpeed);
        }
        else {
            setTelescope(0);
            setBrake(true);
            nextStep();
        }
        if (timer.hasElapsed(Constants.SafteyTimeouts.S6SafetyTimeout)) {
            setTelescope(0);
            setBrake(true);
            nextStep();
        }
        */
    }
    private void S7ConfirmUnhook() {
        if (shouldClimb() || shouldClimbHigh()) {
            nextStep();
        }
    }
    private void S8UnhookAndRetractTelescope() {
        if (shouldInit()) {
            setHooks(true);
        }
        if (timer.hasElapsed(Constants.Climber.S8TimeDelay))
            nextStep();
        /*
        if (shouldInit()) {
            resetTelecopeEncoders();
            setBrake(false);
            setHooks(true);
        }
        if (getTelecopeEncoderPosition() < Constants.ClicksRetractTelescopeMajorly) {
            setTelescope(Constants.TelescopeRetractSpeed);
        }
        else {
            setTelescope(0);
            setBrake(true);
            if (timer.hasElapsed(Constants.S8TimeDelay))
                nextStep();
        }
        if (timer.hasElapsed(Constants.SafteyTimeouts.S8SafetyTimeout)) {
            setTelescope(0);
            setBrake(true);
            if (timer.hasElapsed(Constants.S8TimeDelay))
                nextStep();
        }
        */
    }
    private void S9DefaultAndDisabled() {
        if (shouldInit()) {
            setBrake(true);
            setLower(false);
            setUpper(false);
            setHooks(false);
        }
    }
    private void S10EStop() {
        if (shouldInit()) {
            components.drive.stopMotor();
            setTelescope(0);
            setBrake(true);
        }
    }
}
