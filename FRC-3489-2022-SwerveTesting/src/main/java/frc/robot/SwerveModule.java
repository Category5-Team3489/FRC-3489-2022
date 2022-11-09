package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.sensors.SensorInitializationStrategy;
import com.ctre.phoenix.sensors.WPI_CANCoder;

import edu.wpi.first.math.controller.PIDController;

public class SwerveModule {
    // drivingMotor odd can ids
    private WPI_TalonFX drivingMotor;
    // steeringMotor even can ids
    private WPI_TalonFX steeringMotor;
    // steeringEncoder
    private WPI_CANCoder steeringEncoder;
     
    // steeringController
    private PIDController steeringController;

    private SwerveModuleLocation location;
    private PidConstants pid;
    private double steeringFrictionConstant;

    // 14.853515625
    // 133.2421875

    // 118.388671875

    public SwerveModule(SwerveModuleLocation location, PidConstants pid, double steeringFrictionConstant) {
        drivingMotor = new WPI_TalonFX(location.getDriveCanId());
        steeringMotor = new WPI_TalonFX(location.getSteeringCanId());
        steeringEncoder = new WPI_CANCoder(location.getSteeringEncoderCanId());
        steeringEncoder.configSensorInitializationStrategy(SensorInitializationStrategy.BootToAbsolutePosition);

        drivingMotor.configFactoryDefault();
        steeringMotor.configFactoryDefault();

        steeringController = new PIDController(pid.getP(), pid.getI(), pid.getD());
        steeringController.enableContinuousInput(0, 360);

        this.location = location;
        this.pid = pid;
        this.steeringFrictionConstant = steeringFrictionConstant;
    }

    public void teleopPeriodic(double targetAngle, double targetSpeed) {
        double measurement = steeringEncoder.getAbsolutePosition(); // 0..360
        double setpoint = targetAngle; // 0..360
        double output = steeringController.calculate(measurement, setpoint);
        /*
        if (Math.abs(output) < steeringFrictionConstant) { // 0..steeringFrictionConstant%
            steeringMotor.stopMotor();
        }
        else { // steeringFrictionConstant..100%
            if (output < 0) { // positive
                output += steeringFrictionConstant;
            }
            else { // negative
                output -= steeringFrictionConstant;
            }
            steeringMotor.set(output);
        }
        */
        steeringMotor.set(output);

        drivingMotor.set(targetSpeed);
    }

    public void stopMotors() {
        drivingMotor.stopMotor();
        steeringMotor.stopMotor();
    }

    public void printAngles() {
        String module = location.toString();
        double position = steeringEncoder.getAbsolutePosition();

        System.out.println(module + " " + position);
    }
}
