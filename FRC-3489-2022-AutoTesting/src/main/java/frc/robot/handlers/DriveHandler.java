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
        //return components.leftDriveJoystick.getRawButtonPressed(Constants.SwitchFront) || components.rightDriveJoystick.getRawButtonPressed(Constants.SwitchFront);
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
        if (Math.abs(leftY) < 0.1) leftY = 0;
        if (Math.abs(rightY) < 0.1) rightY = 0;
        double leftSpeed;
        double rightSpeed;
        boolean usedLeftLimiter = false;
        boolean usedRightLimiter = false;
        if (isFront) {
            if (leftY < 0) {
                usedLeftLimiter = true;
                leftY = leftLimiter.calculate(leftY);
            }
            if (rightY < 0) {
                usedRightLimiter = true;
                rightY = rightLimiter.calculate(rightY);
            }
            leftSpeed = -leftY;
            rightSpeed = -rightY;
        }
        else {
            if (leftY > 0) {
                usedLeftLimiter = true;
                leftY = leftLimiter.calculate(leftY);
            }
            if (rightY > 0) {
                usedRightLimiter = true;
                rightY = rightLimiter.calculate(rightY);
            }
            leftSpeed = rightY;
            rightSpeed = leftY;
        }
        if (!usedLeftLimiter)
            leftLimiter.calculate(leftY);
        if (!usedRightLimiter)
            rightLimiter.calculate(leftY);

        components.drive.tankDrive(leftSpeed, rightSpeed);
    }

    public boolean isFront() {
        return isFront;
    }

}
