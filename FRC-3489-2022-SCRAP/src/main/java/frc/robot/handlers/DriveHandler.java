package frc.robot.handlers;

import frc.robot.Constants;
import frc.robot.framework.RobotHandler;

public class DriveHandler extends RobotHandler {
    
    private boolean isFrontSwitched = false;

    @Override
    public void teleopPeriodic() {

        pollAutoShoot();
        pollSwitchFront();

        if (!autoShoot.isAutoShooting()) {
            drive();
        }
    }

    private void pollAutoShoot() {
        if (components.manipulatorJoystick.getRawButtonPressed(Constants.Buttons.AutoShoot)) {
            if (autoShoot.isAutoShooting()) { // auto shooting -> driving
                takeControlBack();
            }
            else { // driving -> auto shooting
                giveControlAway();
            }
        }
    }

    private void pollSwitchFront() {
        /*
        if (components.leftDriveJoystick.getRawButton(1)) {
            isFrontSwitched = !isFrontSwitched;
        }
        */
    }

    private void drive() {
        double leftY = components.leftDriveJoystick.getY();
        double rightY = components.rightDriveJoystick.getY();

        // When auto shooting and joysticks are trying to move the robot, stop auto shooting
        if (autoShoot.isAutoShooting() && (Math.abs(leftY) > Constants.Drive.CancelAutoShootThreshold || Math.abs(rightY) > Constants.Drive.CancelAutoShootThreshold)) {
            takeControlBack();
        }

        double leftSpeed = leftY;
        double rightSpeed = rightY;

        // Add joysick deadzone, so no joystick drift
        if (Math.abs(leftY) < Constants.Drive.JoystickDeadzone) {
            leftSpeed = 0;
        }
        if (Math.abs(rightY) < Constants.Drive.JoystickDeadzone) {
            rightSpeed = 0;
        }

        if (isFrontSwitched) { // Reverse
            components.drive.tankDrive(rightSpeed, leftSpeed);
        }
        else { // Normal
            components.drive.tankDrive(-leftSpeed, -rightSpeed);
        }
    }

    // Handling shared control of the drive train
    public void giveControlBack() {
        autoShoot.setState(AutoShootHandler.State.Driving);
    }
    public void giveControlAway() {
        autoShoot.setState(AutoShootHandler.State.Aiming);
        autoShoot.gaveControl();
    }
    public void takeControlBack() {
        autoShoot.setState(AutoShootHandler.State.Driving);
        autoShoot.tookControl();
    }
}
