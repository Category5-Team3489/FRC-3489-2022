package frc.robot.handlers;

import frc.robot.framework.RobotHandler;

public class DriveHandler extends RobotHandler {
    
    private boolean isFrontSwitched = false;

    @Override
    public void teleopPeriodic() {
        //switchFront();
        drive();
    }

    /*
    private boolean switchFront() {
        if (components.leftDriveJoystick.getRawButton(1)) {
            switchFront = !switchFront;
        }
        return switchFront;
    }
    */

    private void drive() {
        double leftY = components.leftDriveJoystick.getY();
        double rightY = components.rightDriveJoystick.getY();

        double leftSpeed = leftY;
        double rightSpeed = rightY;

        // Add joysick deadzone, so no joystick drift
        if (Math.abs(leftY) < 0.1) leftSpeed = 0;
        if (Math.abs(rightY) < 0.1) rightSpeed = 0;

        if (isFrontSwitched) { // Reverse
            components.drive.tankDrive(rightSpeed, leftSpeed);
        }
        else { // Normal
            components.drive.tankDrive(-leftSpeed, -rightSpeed);
        }
    }

}
