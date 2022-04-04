package frc.robot.handlers;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.Constants;
import frc.robot.framework.RobotHandler;
import frc.robot.interfaces.IShuffleboardState;
import frc.robot.types.DriveState;
import frc.robot.types.LimelightMode;

public class DriveHandler extends RobotHandler implements IShuffleboardState {

    // State
    private boolean isFront = true;
    private boolean driveStateInit = false;
    private DriveState driveState = DriveState.Driving;
    private double distanceEstimate = 0;
    private Timer shootingTimer = new Timer();
    private Timer autoAimTimer = new Timer();

    // Slew limiting
    //private SlewRateLimiter leftLimiter = new SlewRateLimiter(Constants.DriveSetSpeedDeltaLimiter);
    //private SlewRateLimiter rightLimiter = new SlewRateLimiter(Constants.DriveSetSpeedDeltaLimiter);

    // Limelight
    //private NetworkTable limelight = NetworkTableInstance.getDefault().getTable("limelight");
    //private NetworkTableEntry targetX = limelight.getEntry("tx");
    //private NetworkTableEntry targetY = limelight.getEntry("ty");
    //private NetworkTableEntry targetArea = limelight.getEntry("ta");

    // PID controllers
    private PIDController aimController = new PIDController(0.0025 * 2, 0, 0); // 0.0125, 0.004, 0.0001
    private PIDController centerController = new PIDController(0.0025 * 1.75 * 1.5, 0, 0);

    public boolean isFront() {
        return isFront;
    }
    
    public DriveState getDriveState() {
        return driveState;
    }

    @Override
    public void robotInit() {
        aimController.setSetpoint(0);
        aimController.setTolerance(Constants.Drive.AimTolerance);
        centerController.setSetpoint(0);
        centerController.setTolerance(Constants.Drive.CenterTolerance);
        limelightHandler.setLimelightMode(LimelightMode.Driver);
    }

    @Override
    public void robotPeriodic() {
        distanceEstimate = getDistanceEstimate();
        shuffleboardHandler.showDouble(true, "Distance Estimate", distanceEstimate);
    }

    @Override
    public void teleopPeriodic() {
        shuffleboardHandler.showBoolean(true, "Auto Aiming", driveState == DriveState.Shooting);

        if (climberHandler.isClimbing())
            return;

        /*
        boolean switchFrontPressed = shouldSwitchFront();
        if (switchFrontPressed) {
            isFront = !isFront;
            setShuffleboardState();
        }
        */

        if (components.manipulatorJoystick.getRawButtonPressed(Constants.ButtonAimCenterShoot)) {
            if (driveState == DriveState.Driving) {
                setDriveState(DriveState.Aiming);
                aimController.reset();
                centerController.reset();
                shootingTimer.stop();
                shootingTimer.reset();
                limelightHandler.setLimelightMode(LimelightMode.AutoAim);
                autoAimTimer.reset();
                autoAimTimer.start();
            }
            else {
                setDriveState(DriveState.Driving);
                limelightHandler.setLimelightMode(LimelightMode.Driver);
            }
        }

        switch (driveState) {
            case Driving:
                drive();
                break;
            case Aiming:
                aim();
                break;
            case Centering:
                center();
                break;
            case Shooting:
                shoot();
                break;
        }
    }

    @Override
    public void setShuffleboardState() {
        //shuffleboardHandler.setString(true, "Front Switched", isFront ? "Forward" : "Backward");
    }

    private void drive() {
        if (shouldInit()) {

        }
        double leftY = components.leftDriveJoystick.getY();
        double rightY = components.rightDriveJoystick.getY();
        //double leftSpeed = leftLimiter.calculate(leftY);
        //double rightSpeed = rightLimiter.calculate(rightY);
        double leftSpeed = leftY;
        double rightSpeed = rightY;
        if (Math.abs(leftY) < 0.1) leftSpeed = 0;
        if (Math.abs(rightY) < 0.1) rightSpeed = 0;
        if (isFront)
            components.drive.tankDrive(-leftSpeed, -rightSpeed);
        else
            components.drive.tankDrive(rightSpeed, leftSpeed);
    }

    private void aim() {
        if (shouldInit()) {
            
        }
        /*
        // test friction overcome speed
        double speed = components.manipulatorJoystick.getX();
        System.out.println(speed);
        components.drive.tankDrive(speed, speed);
        return;
        */
        double targetXOffset = limelightHandler.x;
        double aimControllerOutput = aimController.calculate(targetXOffset);
        double frictionConstant = aimControllerOutput > 0 ? Constants.Drive.AimFrictionMotorSpeed : -Constants.Drive.AimFrictionMotorSpeed;
        double speed = frictionConstant + aimControllerOutput;
        if (!aimController.atSetpoint()) {
            components.drive.tankDrive(-speed, speed);
        }
        else {
            components.drive.stopMotor();
            setDriveState(DriveState.Centering);
        }
    }

    public boolean autoAim() {
        if (!limelightHandler.isTargetVisible()) {
            return true;
        }
        double targetXOffset = limelightHandler.x;
        double aimControllerOutput = aimController.calculate(targetXOffset);
        double frictionConstant = aimControllerOutput > 0 ? Constants.Drive.AimFrictionMotorSpeed : -Constants.Drive.AimFrictionMotorSpeed;
        double speed = frictionConstant + aimControllerOutput;
        if (!aimController.atSetpoint()) {
            components.drive.tankDrive(-speed, speed);
        }
        else {
            components.drive.stopMotor();
            return true;
        }
        return false;
    }

    private void center() {
        if (distanceEstimate == -1) {
            setDriveState(DriveState.Driving);
        }
        if (shouldInit()) {

        }
        double centerControllerOutput = centerController.calculate(distanceEstimate - Constants.Drive.ShootingDistance);
        double frictionConstant = centerControllerOutput > 0 ? Constants.Drive.CenterFrictionMotorSpeed : -Constants.Drive.CenterFrictionMotorSpeed;
        double speed = frictionConstant + centerControllerOutput;
        if (!centerController.atSetpoint()) {
            components.drive.tankDrive(speed, speed);
        }
        else {
            components.drive.stopMotor();
            setDriveState(DriveState.Shooting);
        }
    }

    private void shoot() {
        if (shouldInit()) {
            shootingTimer.reset();
            shootingTimer.start();
            shooterHandler.shootHigh();
        }
        if (shootingTimer.hasElapsed(Constants.Drive.ShooterDelay)) {
            // Run cargo mover
            cargoTransferHandler.setShootSpeed();
        }
        if (shootingTimer.hasElapsed(Constants.Drive.ShooterDelay + Constants.Drive.ShootTime)) {
            // Stop cargo mover
            // Switch drive state back to normal teleop driving
            cargoTransferHandler.stop();
            shooterHandler.stop();
            setDriveState(DriveState.Driving);
        }
    }

    private double getDistanceEstimate()
    {
        double targetYOffset = limelightHandler.y;
        if (!limelightHandler.isTargetVisible())
            return -1;
        double distance = 67.625 / Math.tan(Math.toRadians(46.13 + targetYOffset));
        return distance;
    }

    /*
    private boolean shouldSwitchFront() {
        return components.rightDriveJoystick.getRawButtonPressed(Constants.ButtonSwitchFront) ||
            components.rightDriveJoystick.getRawButtonPressed(Constants.ButtonSwitchFrontB);
    }
    */

    private boolean shouldInit() {
        if (driveStateInit)
            return false;
        driveStateInit = true;
        return true;
    }

    private void setDriveState(DriveState driveState) {
        this.driveState = driveState;
        driveStateInit = false;
        shuffleboardHandler.setString(true, "Drive State", driveState.toString());
    }
}
