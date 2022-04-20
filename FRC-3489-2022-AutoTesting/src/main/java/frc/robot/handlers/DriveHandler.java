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

    private int loop = 0;

    private Diagnostic diagnostic;

    // Slew limiting
    //private SlewRateLimiter leftLimiter = new SlewRateLimiter(Constants.DriveSetSpeedDeltaLimiter);
    //private SlewRateLimiter rightLimiter = new SlewRateLimiter(Constants.DriveSetSpeedDeltaLimiter);

    // Limelight
    //private NetworkTable limelight = NetworkTableInstance.getDefault().getTable("limelight");
    //private NetworkTableEntry targetX = limelight.getEntry("tx");
    //private NetworkTableEntry targetY = limelight.getEntry("ty");
    //private NetworkTableEntry targetArea = limelight.getEntry("ta");

    // PID controllers
    public PIDController aimController = new PIDController(0.0025 * 2 * 1.25, 0, 0.0); // 0.0125, 0.004, 0.0001
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
        setDriveState(DriveState.Driving);
    }

    @Override
    public void robotPeriodic() {
        distanceEstimate = getDistanceEstimate();
        shuffleboardHandler.showDouble(true, "Distance Estimate", distanceEstimate);
        loop++;
    }

    @Override
    public void teleopPeriodic() {
        shuffleboardHandler.showBoolean(true, "Auto Aiming", driveState == DriveState.Shooting);

        if (climberHandler.isClimbing()) {
            if (getDriveState() != DriveState.Driving) {
                toggleToDrive();
            }
            return;
        }

        if (getDriveState() != DriveState.Driving && (Math.abs(components.leftDriveJoystick.getY()) > 0.4 || Math.abs(components.rightDriveJoystick.getY()) > 0.4))
            toggleToDrive();

            /*
            if(components.rightDriveJoystick.getRawButtonPressed(Constants.Buttons.RunDiagnostic)){
                diagnostic.getBaseLine();
            }
            */
        /*
        boolean switchFrontPressed = shouldSwitchFront();
        if (switchFrontPressed) {
            isFront = !isFront;
            setShuffleboardState();
        }
        */

        if (components.manipulatorJoystick.getRawButtonPressed(Constants.Buttons.AimCenterShoot)) {
            if (driveState == DriveState.Driving) {
                toggleToAim();
            }
            else {
                toggleToDrive();
            }
        }

        switch (driveState) {
            case Driving:
                drive();
                /*
                if (loop % 50 == 0) {
                    System.out.println("Distance: " + distanceEstimate);
                }
                */
                break;
            case Aiming:
                double targetXOffset = limelightHandler.x;
                if (targetXOffset != 100) {
                    aim(false, targetXOffset);
                }
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

    private void toggleToDrive() {
        setDriveState(DriveState.Driving);
        shooterHandler.stop();
        cargoTransferHandler.stop();
        limelightHandler.setLimelightMode(LimelightMode.Driver);
    }

    private void toggleToAim() {
        setDriveState(DriveState.Aiming);
        aimController.reset();
        centerController.reset();
        shootingTimer.stop();
        shootingTimer.reset();
        limelightHandler.setLimelightMode(LimelightMode.AutoAim);
        autoAimTimer.reset();
        autoAimTimer.start();
    }

    private void drive() {
        if (shouldInit()) {

        }
        double leftY = components.leftDriveJoystick.getY();
        double rightY = components.rightDriveJoystick.getY();
        //double leftSpeed = leftLimiter.calculate(leftY);
        //double rightSpeed = rightLimiter.calculate(rightY);
        //double throttle = components.leftDriveJoystick.getThrottle();
        //double setting = GeneralUtils.lerp(0, 4, (throttle + 1) / 2d);
        //System.out.println(setting);
        double leftSpeed = leftY;//getScaledSpeed(leftY, setting);
        double rightSpeed = rightY;//getScaledSpeed(rightY, setting);;
        if (Math.abs(leftY) < 0.1) leftSpeed = 0;
        if (Math.abs(rightY) < 0.1) rightSpeed = 0;
        if (isFront)
            components.drive.tankDrive(-leftSpeed, -rightSpeed);
        else
            components.drive.tankDrive(rightSpeed, leftSpeed);
    }

    /*
    private double getScaledSpeed(double input, double setting) {
        return Math.pow(Math.abs(input), setting) * input > 0 ? 1 : -1;
    }
    */

    public void aim(boolean keepAiming, double targetXOffset) {
        if (shouldInit()) {
            
        }
        // test friction overcome speed
        /*
        double speed = components.manipulatorJoystick.getX();
        System.out.println(speed);
        components.drive.tankDrive(speed, -speed);
        */
        //return;
        double aimControllerOutput = aimController.calculate(targetXOffset);
        double frictionConstant = aimControllerOutput > 0 ? Constants.Drive.AimFrictionMotorSpeed : -Constants.Drive.AimFrictionMotorSpeed;
        double speed = frictionConstant + aimControllerOutput;
        if (!aimController.atSetpoint()) {
            components.drive.tankDrive(-speed, speed);
        }
        else {
            components.drive.stopMotor();
            if (!keepAiming)
                setDriveState(DriveState.Shooting);
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
        if (Math.abs(limelightHandler.x) > 4)
            toggleToAim();

        shooterHandler.addValue("B CP100ms", components.bottomShooterMotor.getSelectedSensorVelocity());
        shooterHandler.addValue("T CP100ms", -components.topShooterMotor.getSelectedSensorVelocity());
        if (shooterHandler.currentSetting != null) {
            shooterHandler.addValue("S B CP100ms", shooterHandler.currentSetting.bottomSpeed);
            shooterHandler.addValue("S T CP100ms", shooterHandler.currentSetting.topSpeed);
        }
        if (shouldInit()) {

            shootingTimer.reset();
            shootingTimer.start();
            if (distanceEstimate == -1) {
                //shooterHandler.shootHigh();
                shooterHandler.stop();
            }
            else {
                //shooterHandler.setShooterAtDistance(distanceEstimate);
                System.out.println(distanceEstimate);
            }
        }
        // TODO Okay?
        shooterHandler.setShooterAtDistance(distanceEstimate);
        double targetXOffset = limelightHandler.x;
        if (targetXOffset != 100) {
            aim(true, targetXOffset);
        }
        if (shooterHandler.readyToShoot()) {
            // Run cargo mover
            cargoTransferHandler.setShootSpeed();
            intakeHandler.forwardIntake(false);
        }
        if (shootingTimer.hasElapsed(Constants.Drive.ShooterDelay + Constants.Drive.ShootTime)) {
            // Stop cargo mover
            // Switch drive state back to normal teleop driving
            System.out.println("Shot: " + Timer.getMatchTime());
            System.out.println("DE: " + distanceEstimate);
            System.out.println("Actual Vel: " + components.bottomShooterMotor.getSelectedSensorVelocity() + " : " + components.topShooterMotor.getSelectedSensorVelocity());
            if (shooterHandler.currentSetting != null) {
                System.out.println("Set Vel: " + shooterHandler.currentSetting.bottomSpeed + " : " + shooterHandler.currentSetting.topSpeed);
            }
            cargoTransferHandler.stop();
            shooterHandler.stop();
            intakeHandler.stop();
            cargoSystemHandler.setCargoCount(0);
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
