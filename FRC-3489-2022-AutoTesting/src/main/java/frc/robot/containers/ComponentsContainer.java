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

    // Motors
    public WPI_TalonSRX leftFrontDriveMotor = new WPI_TalonSRX(1);
    public WPI_TalonSRX rightFrontDriveMotor = new WPI_TalonSRX(2);
    public WPI_TalonSRX leftFollowerDriveMotor = new WPI_TalonSRX(3);
    public WPI_TalonSRX rightFollowerDriveMotor = new WPI_TalonSRX(4);
    public DifferentialDrive drive;

    public WPI_TalonSRX cargoTransferMotor = new WPI_TalonSRX(5);
    public WPI_TalonSRX intakeMotor = new WPI_TalonSRX(6);
    public WPI_TalonFX bottomShooterMotor = new WPI_TalonFX(7);
    public WPI_TalonFX topShooterMotor = new WPI_TalonFX(8);
    public WPI_TalonFX climbMotor = new WPI_TalonFX(9);

    // Climb
    public WPI_TalonFX ClimbMotor = new WPI_TalonFX(10);
    public Solenoid topLeftSolenoid = new Solenoid(36, PneumaticsModuleType.REVPH, 1);
    public Solenoid topRightSolenoid = new Solenoid(36, PneumaticsModuleType.REVPH, 2);
    public Solenoid bottomLeftSolenoid = new Solenoid(36, PneumaticsModuleType.REVPH, 3);
    public Solenoid bottomRightSolenoid = new Solenoid(36, PneumaticsModuleType.REVPH, 4);
    public Solenoid brakeSolenoid = new Solenoid(36, PneumaticsModuleType.REVPH, 5);
    public Solenoid hookSolenoid = new Solenoid(36, PneumaticsModuleType.REVPH, 6);

    public ComponentsContainer() {

        /*
        leftFrontDriveMotor.configOpenloopRamp(Constants.DriveSetSpeedDeltaLimiter);
        rightFrontDriveMotor.configOpenloopRamp(Constants.DriveSetSpeedDeltaLimiter);
        leftFollowerDriveMotor.configOpenloopRamp(Constants.DriveSetSpeedDeltaLimiter);
        rightFollowerDriveMotor.configOpenloopRamp(Constants.DriveSetSpeedDeltaLimiter);
        */

        leftDriveJoystick = new Joystick(0);
        rightDriveJoystick = new Joystick(1);
        manipulatorJoystick = new Joystick(2);

        setSafeties(drive = new DifferentialDrive(leftFrontDriveMotor, rightFrontDriveMotor));

        setSafeties(leftFrontDriveMotor);
        setSafeties(rightFrontDriveMotor);
        setSafeties(leftFollowerDriveMotor);
        setSafeties(rightFollowerDriveMotor);

        setSafeties(cargoTransferMotor);
        setSafeties(intakeMotor);
        setSafeties(bottomShooterMotor);
        setSafeties(topShooterMotor);
        setSafeties(climbMotor);

        defaultDriveMotors();
        defaultMotors();
    }

    public void defaultDriveMotors() {
        leftFrontDriveMotor.configFactoryDefault();
        rightFrontDriveMotor.configFactoryDefault();
        leftFollowerDriveMotor.configFactoryDefault();
        rightFollowerDriveMotor.configFactoryDefault();
    }

    public void defaultMotors() {
        cargoTransferMotor.configFactoryDefault();
        intakeMotor.configFactoryDefault();
        bottomShooterMotor.configFactoryDefault();
        topShooterMotor.configFactoryDefault();
        climbMotor.configFactoryDefault();
    }

    public void disableDriveMotors() {
        leftFrontDriveMotor.stopMotor();
        rightFrontDriveMotor.stopMotor();
        leftFollowerDriveMotor.stopMotor();
        rightFollowerDriveMotor.stopMotor();
    }

    public void disableMotors() {
        cargoTransferMotor.stopMotor();
        intakeMotor.stopMotor();
        bottomShooterMotor.stopMotor();
        topShooterMotor.stopMotor();
        climbMotor.stopMotor();
    }

    public void configNominalDrive() {
        defaultDriveMotors();
        disableDriveMotors();
        rightFrontDriveMotor.setInverted(true);
        rightFollowerDriveMotor.setInverted(true);
        leftFollowerDriveMotor.follow(leftFrontDriveMotor, FollowerType.PercentOutput);
        rightFollowerDriveMotor.follow(rightFrontDriveMotor, FollowerType.PercentOutput);
    }

    public void configAutoPIDDrive(int timeout, double speed, double kF, double kP, double kI, double kD, double Iz) {
        defaultDriveMotors();
        disableDriveMotors();
        rightFrontDriveMotor.setInverted(true);
        rightFollowerDriveMotor.setInverted(true);
        leftFollowerDriveMotor.follow(leftFrontDriveMotor, FollowerType.PercentOutput);
        rightFrontDriveMotor.follow(leftFrontDriveMotor, FollowerType.PercentOutput);
        rightFollowerDriveMotor.follow(rightFrontDriveMotor, FollowerType.PercentOutput);

        leftFrontDriveMotor.setSensorPhase(true);

        leftFrontDriveMotor.configNominalOutputForward(0, timeout);
		leftFrontDriveMotor.configNominalOutputReverse(0, timeout);
		leftFrontDriveMotor.configPeakOutputForward(speed, timeout);
		leftFrontDriveMotor.configPeakOutputReverse(-speed, timeout);

        leftFrontDriveMotor.configAllowableClosedloopError(0, 0, timeout);

        leftFrontDriveMotor.config_kF(0, kF, timeout);
		leftFrontDriveMotor.config_kP(0, kP, timeout);
		leftFrontDriveMotor.config_kI(0, kI, timeout);
		leftFrontDriveMotor.config_kD(0, kD, timeout);
        leftFrontDriveMotor.config_IntegralZone(0, Iz, timeout);

        leftFrontDriveMotor.setSelectedSensorPosition(0, 0, timeout);
    }

    public void configAutoPIDTurn() {
        defaultDriveMotors();
        disableDriveMotors();
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
