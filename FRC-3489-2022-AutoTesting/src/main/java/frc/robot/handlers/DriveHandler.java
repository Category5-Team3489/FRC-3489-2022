package frc.robot.handlers;

import frc.robot.framework.RobotHandler;

public class DriveHandler extends RobotHandler {

    private boolean frontSwitched = false;

    private boolean shouldSwitchFront() {
        return components.leftDriveJoystick.getRawButtonPressed(13) || components.rightDriveJoystick.getRawButtonPressed(13);
    }

    @Override
    public void teleopPeriodic() {
        if (shouldSwitchFront())
            frontSwitched = !frontSwitched;
        double leftY = components.leftDriveJoystick.getY();
        double rightY = components.rightDriveJoystick.getY();
        double leftSpeed = 0;
        double rightSpeed = 0;
        if (Math.abs(leftY) >= 0.1) leftSpeed = leftY;
        if (Math.abs(rightY) >= 0.1) rightSpeed = rightY;
        if (frontSwitched)
            components.drive.tankDrive(rightSpeed, leftSpeed);
        else
            components.drive.tankDrive(-leftSpeed, -rightSpeed);
    }

    public boolean isFrontSwitched() {
        return frontSwitched;
    }

}
