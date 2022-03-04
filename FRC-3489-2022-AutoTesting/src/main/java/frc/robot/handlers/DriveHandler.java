package frc.robot.handlers;

import edu.wpi.first.math.filter.SlewRateLimiter;
import frc.robot.Constants;
import frc.robot.framework.RobotHandler;

public class DriveHandler extends RobotHandler {

    private boolean isFront = true;

    private SlewRateLimiter leftLimiter = new SlewRateLimiter(Constants.DriveSetSpeedDeltaLimiter);
    private SlewRateLimiter rightLimiter = new SlewRateLimiter(Constants.DriveSetSpeedDeltaLimiter);

    private boolean shouldSwitchFront() {
        return components.rightDriveJoystick.getRawButtonPressed(Constants.SwitchFront);
    }

    @Override
    public void teleopPeriodic() {
        //Switch driving direction
        if (shouldSwitchFront()) {
            isFront = !isFront;
            cameraHandler.setCamera(isFront);
            shuffleboardHandler.setString(true, "Drive Mode", isFront ? "Forward" : "Backward");
        }
        double leftY = components.leftDriveJoystick.getY();
        double rightY = components.rightDriveJoystick.getY();
        double leftSpeed = leftLimiter.calculate(leftY);
        double rightSpeed = rightLimiter.calculate(rightY);
        if (Math.abs(leftY) < 0.1) leftSpeed = 0;
        if (Math.abs(rightY) < 0.1) rightSpeed = 0;
        if (isFront)
            components.drive.tankDrive(-leftSpeed, -rightSpeed);
        else
            components.drive.tankDrive(rightSpeed, leftSpeed);
    }

    public boolean isFront() {
        return isFront;
    }

}
