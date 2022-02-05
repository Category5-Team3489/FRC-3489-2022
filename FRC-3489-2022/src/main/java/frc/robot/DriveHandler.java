package frc.robot;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.framework.RobotHandler;

public class DriveHandler extends RobotHandler {

    public DifferentialDrive differentialDrive;

    public void robotInit() {
        components.backLeftDrive.follow(components.frontLeftDrive);
        components.backRightDrive.follow(components.frontRightDrive);
        components.frontRightDrive.setInverted(true);
        differentialDrive = new DifferentialDrive(components.frontLeftDrive, components.frontRightDrive);
    }

    public void teleopPeriodic() {
        driveHandler.tankDrive(joystickHandler.getLeftJoystickYValue(), joystickHandler.getRightJoystickYValue());
    }



    public void tankDrive(double leftSpeed, double rightSpeed) {
        differentialDrive.tankDrive(leftSpeed, rightSpeed);
    }

    public void stop() {
        differentialDrive.stopMotor();
    }
}
