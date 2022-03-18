package frc.robot.handlers;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import frc.robot.Constants;
import frc.robot.framework.RobotHandler;

public class DriveHandler extends RobotHandler {

    private boolean isFront = true;
    private boolean isDriving = true;

    private SlewRateLimiter leftLimiter = new SlewRateLimiter(Constants.DriveSetSpeedDeltaLimiter);
    private SlewRateLimiter rightLimiter = new SlewRateLimiter(Constants.DriveSetSpeedDeltaLimiter);

    private NetworkTable limelight = NetworkTableInstance.getDefault().getTable("limelight");
    private NetworkTableEntry pipeline = limelight.getEntry("pipeline");
    private NetworkTableEntry targetX = limelight.getEntry("tx");
    private NetworkTableEntry targetY = limelight.getEntry("ty");
    private NetworkTableEntry targetArea = limelight.getEntry("ta");

    @Override
    public void teleopPeriodic() {
        if (buttonHandler.switchFrontPressed()) {
            isFront = !isFront;
            shuffleboardHandler.setString(true, "Drive Mode", isFront ? "Forward" : "Backward");
        }
        if (components.manipulatorJoystick.getRawButtonPressed(4)) {
            isDriving = !isDriving;
            if (isDriving) {
                // set pipeline
            }
            else {

            }
        }
        if (isDriving) {
            drive();
        }
        else {
            aim();
        }
    }

    private void drive() {
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

    private void aim() {
        double targetXOffset = targetX.getDouble(0);
        double adjustSpeed = Math.abs(targetXOffset) * 0.005;
        System.out.println(getDistanceEstimate());
        double speed = Constants.AutoAimFrictionOvercomeMotorSpeed + adjustSpeed;
        //double speed = components.manipulatorJoystick.getX();
        //shuffleboardHandler.setDouble(true, "Debug Speed", speed);
        if (targetXOffset < 1.5) {
            components.drive.tankDrive(-speed, speed);
        }
        else if (targetXOffset > 1.5) {
            components.drive.tankDrive(speed, -speed);
        }
        else {
            components.drive.stopMotor();
        }
    }

    private double getDistanceEstimate()
    {
        double targetYOffset = targetY.getDouble(0);
        if (targetYOffset == 0) return -1;
        double distance = 67 / Math.sin((47 + targetYOffset) * 0.01745);
        return distance;
    }

    public boolean isFront() {
        return isFront;
    }

}
