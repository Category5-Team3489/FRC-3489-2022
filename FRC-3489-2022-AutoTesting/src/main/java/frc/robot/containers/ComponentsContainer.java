package frc.robot.containers;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Servo;
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
    public AHRS navx = new AHRS(SPI.Port.kMXP, (byte)200);

    // Servos
    public Servo cameraServo = new Servo(7);

    // Motors
    public CANSparkMax leftFrontDriveMotor = new CANSparkMax(1, MotorType.kBrushless);
    public CANSparkMax rightFrontDriveMotor = new CANSparkMax(2, MotorType.kBrushless);
    public CANSparkMax leftFollowerDriveMotor = new CANSparkMax(3, MotorType.kBrushless);
    public CANSparkMax rightFollowerDriveMotor = new CANSparkMax(4, MotorType.kBrushless);
    public DifferentialDrive drive;

    public WPI_TalonSRX cargoTransferMotor = new WPI_TalonSRX(5);
    public WPI_TalonFX intakeMotor = new WPI_TalonFX(6);
    public WPI_TalonFX bottomShooterMotor = new WPI_TalonFX(7);
    public WPI_TalonFX topShooterMotor = new WPI_TalonFX(8);
    public WPI_TalonFX telescopeMotor = new WPI_TalonFX(9);

    // Climb
    public Solenoid lowerSolenoid = new Solenoid(36, PneumaticsModuleType.REVPH, 2);
    public Solenoid upperSolenoid = new Solenoid(36, PneumaticsModuleType.REVPH, 3);
    public Solenoid hookSolenoid = new Solenoid(36, PneumaticsModuleType.REVPH, 4);
    public Solenoid brakeSolenoid = new Solenoid(36, PneumaticsModuleType.REVPH, 5);

    // Intake
    public Solenoid intakeSolenoid = new Solenoid(36, PneumaticsModuleType.REVPH, 6);

    public ComponentsContainer() {

        leftDriveJoystick = new Joystick(0);
        rightDriveJoystick = new Joystick(1);
        manipulatorJoystick = new Joystick(2);

        drive = new DifferentialDrive(leftFrontDriveMotor, rightFrontDriveMotor);

        setSafeties(drive);

        setSafeties(cargoTransferMotor);
        setSafeties(intakeMotor);
        setSafeties(bottomShooterMotor);
        setSafeties(topShooterMotor);
        setSafeties(telescopeMotor);

        defaultDriveMotors();
        defaultMotors();

        configNominalDrive();
    }

    public void defaultDriveMotors() {
        leftFrontDriveMotor.restoreFactoryDefaults();
        rightFrontDriveMotor.restoreFactoryDefaults();
        leftFollowerDriveMotor.restoreFactoryDefaults();
        rightFollowerDriveMotor.restoreFactoryDefaults();

        leftFrontDriveMotor.setIdleMode(IdleMode.kBrake);
        rightFrontDriveMotor.setIdleMode(IdleMode.kBrake);
        leftFollowerDriveMotor.setIdleMode(IdleMode.kBrake);
        rightFollowerDriveMotor.setIdleMode(IdleMode.kBrake);
    }

    public void defaultMotors() {
        cargoTransferMotor.configFactoryDefault();
        intakeMotor.configFactoryDefault();
        bottomShooterMotor.configFactoryDefault();
        topShooterMotor.configFactoryDefault();
        telescopeMotor.configFactoryDefault();
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
        telescopeMotor.stopMotor();
    }

    public void configNominalDrive() {
        defaultDriveMotors();
        disableDriveMotors();
        rightFrontDriveMotor.setInverted(true);
        rightFollowerDriveMotor.setInverted(true);
        leftFollowerDriveMotor.follow(leftFrontDriveMotor);
        rightFollowerDriveMotor.follow(rightFrontDriveMotor);
    }

    public void configAutoPIDDrive(int timeout, double speed, double kF, double kP, double kI, double kD, double Iz) {
        /*
        defaultDriveMotors();
        disableDriveMotors();
        rightFrontDriveMotor.setInverted(true);
        rightFollowerDriveMotor.setInverted(true);
        leftFollowerDriveMotor.follow(leftFrontDriveMotor);
        rightFrontDriveMotor.follow(leftFrontDriveMotor);
        rightFollowerDriveMotor.follow(rightFrontDriveMotor);

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
        */
    }

    public void configShooterPID(int timeout, double kF, double kP, double kI, double kD, double Iz) {
        /*
        bottomShooterMotor.configFactoryDefault();
        topShooterMotor.configFactoryDefault();

        bottomShooterMotor.setSensorPhase(true);
        topShooterMotor.setSensorPhase(true);

        bottomShooterMotor.configNominalOutputForward(0, timeout);
		bottomShooterMotor.configNominalOutputReverse(0, timeout);
		leftFrontDriveMotor.configPeakOutputForward(1, timeout);
		leftFrontDriveMotor.configPeakOutputReverse(-1, timeout);
        leftFrontDriveMotor.configAllowableClosedloopError(0, 0, timeout);

        leftFrontDriveMotor.config_kF(0, kF, timeout);
		leftFrontDriveMotor.config_kP(0, kP, timeout);
		leftFrontDriveMotor.config_kI(0, kI, timeout);
		leftFrontDriveMotor.config_kD(0, kD, timeout);
        leftFrontDriveMotor.config_IntegralZone(0, Iz, timeout);

        leftFrontDriveMotor.setSelectedSensorPosition(0, 0, timeout);
        */
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
