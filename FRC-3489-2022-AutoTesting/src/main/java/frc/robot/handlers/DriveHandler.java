package frc.robot.handlers;

import frc.robot.Constants;
import frc.robot.framework.RobotHandler;
import frc.robot.types.RobotType;

public class DriveHandler extends RobotHandler {

    private boolean frontSwitched = false;

    private long loop = 0;

    private boolean shouldSwitchFront() {
        return components.leftDriveJoystick.getRawButtonPressed(13) || components.rightDriveJoystick.getRawButtonPressed(13);
    }

    @Override
    public void teleopPeriodic() {

        if (Constants.SelectedRobot != RobotType.Robot) return;

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

        if (components.leftDriveJoystick.getRawButtonPressed(12) || components.rightDriveJoystick.getRawButtonPressed(12)) {
            components.leftFrontDriveMotor.setSelectedSensorPosition(0);
            components.rightFollowerDriveMotor.setSelectedSensorPosition(0);
        }
        if (loop % 10 == 0 && Constants.SelectedRobot == RobotType.Robot) 
            System.out.println(((int)components.leftFrontDriveMotor.getSelectedSensorPosition()) + ", " + ((int)components.leftFollowerDriveMotor.getSelectedSensorPosition()));
        loop++;
    }

    public boolean isFrontSwitched() {
        return frontSwitched;
    }

}
