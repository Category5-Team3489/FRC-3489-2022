package frc.robot.handlers;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.framework.RobotHandler;

public class DriveHandler extends RobotHandler {
    
    private Joystick leftDriveJoystick = new Joystick(0);
    private Joystick rightDriveJoystick = new Joystick(1);

    private DifferentialDrive drive;

    private boolean frontSwitched = false;

    private boolean shouldSwitchFront() {
        return leftDriveJoystick.getRawButtonPressed(13) || rightDriveJoystick.getRawButtonPressed(13);
    }

    @Override
    public void teleopInit() {
        drive = new DifferentialDrive(components.leftFrontDriveMotor, components.rightFrontDriveMotor);
    }

    @Override
    public void teleopPeriodic() {
        if (shouldSwitchFront())
            frontSwitched = !frontSwitched;
        double leftY = leftDriveJoystick.getY();
        double rightY = rightDriveJoystick.getY();
        double leftSpeed = 0;
        double rightSpeed = 0;
        if (Math.abs(leftY) >= 0.1) leftSpeed = leftY;
        if (Math.abs(rightY) >= 0.1) rightSpeed = rightY;
        if (frontSwitched)
            drive.tankDrive(leftSpeed, rightSpeed);
        else
            drive.tankDrive(-rightSpeed, -leftSpeed);
    }
    
}
