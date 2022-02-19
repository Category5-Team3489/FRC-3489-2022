package frc.robot.containers;

import com.ctre.phoenix.motorcontrol.FollowerType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.Constants;

public final class ComponentsContainer {

    // Joysticks
    public Joystick leftDriveJoystick;
    public Joystick rightDriveJoystick;
    public Joystick manipulatorJoystick;

    // Sensors
    public DigitalInput intakeLaserSensor = new DigitalInput(0);

    // Drive
    public WPI_TalonSRX leftFrontDriveMotor = new WPI_TalonSRX(1);
    public WPI_TalonSRX rightFrontDriveMotor = new WPI_TalonSRX(2);
    public WPI_TalonSRX leftFollowerDriveMotor = new WPI_TalonSRX(3);
    public WPI_TalonSRX rightFollowerDriveMotor = new WPI_TalonSRX(4);
    public DifferentialDrive drive;

    // Ball System
    public WPI_TalonFX bottomShooterMotor = new WPI_TalonFX(5);
    public WPI_TalonFX topShooterMotor = new WPI_TalonFX(6);
    public WPI_TalonSRX cargoTransferMotor = new WPI_TalonSRX(7);
    public WPI_TalonSRX intakeMotor = new WPI_TalonSRX(8);

    // Climb
    public WPI_TalonFX ClimbMotor = new WPI_TalonFX(10);
    public Solenoid topLeftSolenoid = new Solenoid(PneumaticsModuleType.REVPH, 1);
    public Solenoid topRightSolenoid = new Solenoid(PneumaticsModuleType.REVPH, 2);
    public Solenoid bottomLeftSolenoid = new Solenoid(PneumaticsModuleType.REVPH, 3);
    public Solenoid bottomRightSolenoid = new Solenoid(PneumaticsModuleType.REVPH, 4);
    public Solenoid brakeSolenoid = new Solenoid(PneumaticsModuleType.REVPH, 5);
    public Solenoid hookSolenoid = new Solenoid(PneumaticsModuleType.REVPH, 6);

    public ComponentsContainer() {
        leftFrontDriveMotor.configFactoryDefault();
        rightFrontDriveMotor.configFactoryDefault();
        leftFollowerDriveMotor.configFactoryDefault();
        rightFollowerDriveMotor.configFactoryDefault();
        
        bottomShooterMotor.configFactoryDefault();
        topShooterMotor.configFactoryDefault();
        cargoTransferMotor.configFactoryDefault();
        intakeMotor.configFactoryDefault();

        leftFrontDriveMotor.configOpenloopRamp(Constants.DriveSetSpeedDeltaLimiter);
        rightFrontDriveMotor.configOpenloopRamp(Constants.DriveSetSpeedDeltaLimiter);
        leftFollowerDriveMotor.configOpenloopRamp(Constants.DriveSetSpeedDeltaLimiter);
        rightFollowerDriveMotor.configOpenloopRamp(Constants.DriveSetSpeedDeltaLimiter);

        leftDriveJoystick = new Joystick(0);
        rightDriveJoystick = new Joystick(1);
        manipulatorJoystick = new Joystick(2);

        setSafeties(drive = new DifferentialDrive(leftFrontDriveMotor, rightFrontDriveMotor));

        setSafeties(leftFrontDriveMotor);
        setSafeties(rightFrontDriveMotor);
        setSafeties(leftFollowerDriveMotor);
        setSafeties(rightFollowerDriveMotor);

        setSafeties(bottomShooterMotor);
        setSafeties(topShooterMotor);
        setSafeties(cargoTransferMotor);
        setSafeties(intakeMotor);

        setDefaultDrive();
    }

    public void setDefaultDrive() {
        rightFrontDriveMotor.setInverted(true);
        rightFollowerDriveMotor.setInverted(true);
        leftFollowerDriveMotor.follow(leftFrontDriveMotor, FollowerType.PercentOutput);
        rightFollowerDriveMotor.follow(rightFrontDriveMotor, FollowerType.PercentOutput);
    }

    public void setPIDStraightDrive() {
        rightFrontDriveMotor.setInverted(true);
        rightFollowerDriveMotor.setInverted(true);
        leftFollowerDriveMotor.follow(leftFrontDriveMotor, FollowerType.PercentOutput);
        rightFrontDriveMotor.follow(leftFrontDriveMotor, FollowerType.PercentOutput);
        rightFollowerDriveMotor.follow(rightFrontDriveMotor, FollowerType.PercentOutput);
    }

    private void setSafeties(WPI_TalonSRX motor) {
        motor.setSafetyEnabled(Constants.SafetiesEnabled);
    }
    private void setSafeties(WPI_TalonFX motor) {
        motor.setSafetyEnabled(Constants.SafetiesEnabled);
    }
    private void setSafeties(DifferentialDrive drive) {
        drive.setSafetyEnabled(Constants.SafetiesEnabled);
    }

}
