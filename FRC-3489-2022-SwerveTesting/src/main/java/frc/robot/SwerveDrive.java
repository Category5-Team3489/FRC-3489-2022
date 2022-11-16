package frc.robot;

public class SwerveDrive {
    
    private SwerveModule frontLeftModule;
    private SwerveModule frontRightModule;
    private SwerveModule backRightModule;
    private SwerveModule backLeftModule;

    //public final static double ClicksPerRotation = 26214.4;// 32768 * (4 / 5) ////// 26204.07

    private PidConstants pid = new PidConstants(0.007282 * 1.5, 0, 0);
    private double steeringFrictionConstant = 0.075;

    private double targetAngle = 0;

    // gears: 0..33% speed, 0..100%
    
    public SwerveDrive() {
        // setup 4 swerve modules
        frontLeftModule = new SwerveModule(SwerveModuleLocation.FrontLeft, pid, steeringFrictionConstant, -54);
        frontRightModule = new SwerveModule(SwerveModuleLocation.FrontRight, pid, steeringFrictionConstant, -4.7);
        backRightModule = new SwerveModule(SwerveModuleLocation.BackRight, pid, steeringFrictionConstant, -130.3);
        backLeftModule = new SwerveModule(SwerveModuleLocation.BackLeft, pid, steeringFrictionConstant, 73.2);
    }

    public void teleopPeriodic(double x, double y, double debugDriveSpeed) {
        double targetSpeed = Math.sqrt(x * x + y * y); // 0..1

        //targetAngle = Math.atan2(x, y) * 180 / Math.PI + 180; // 0..360

        if (targetSpeed > 0.03) {
            targetAngle = Math.atan2(x, y) * 180 / Math.PI + 180; // 0..360
        }
        else {
            targetSpeed = 0;
        }

        targetSpeed *= 0.25;

        // DEBUG
        //targetSpeed = debugDriveSpeed;

        frontLeftModule.teleopPeriodic(targetAngle, targetSpeed);
        frontRightModule.teleopPeriodic(targetAngle, targetSpeed);
        backRightModule.teleopPeriodic(targetAngle, targetSpeed);
        backLeftModule.teleopPeriodic(targetAngle, targetSpeed);
    }

    public void printAngles() {
        System.out.println(targetAngle);
        frontLeftModule.printAngles();
        frontRightModule.printAngles();
        backRightModule.printAngles();
        backLeftModule.printAngles();
        System.out.println("------------------------------");
    }

    /*
    private double step(double x, double a, double b) {
        return (x - a) / (b - a);
    }

    private double lerp(double a, double b, double t) {
        return a + t * (b - a);
    }
    */
}
