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
    
    // Testing
    public WPI_TalonFX leftTestMotor;
    public WPI_TalonFX rightTestMotor;

    // Ball System
    public DigitalInput intakeLaserSensor;
    public WPI_TalonSRX intakeMotor;
    public WPI_TalonSRX cargoTransferMotor;
    public WPI_TalonFX bottomShooterMotor;
    public WPI_TalonFX topShooterMotor;

    // Joysticks
    public Joystick leftDriveJoystick;
    public Joystick rightDriveJoystick;
    public Joystick manipulatorJoystick;

    // Drive
    public WPI_TalonSRX leftFrontDriveMotor;
    public WPI_TalonSRX rightFrontDriveMotor;
    public WPI_TalonSRX leftFollowerDriveMotor;
    public WPI_TalonSRX rightFollowerDriveMotor;
    public DifferentialDrive drive;

    // Climb 
    public WPI_TalonFX ClimbMotor = new WPI_TalonFX(99999);
    public Solenoid topLeftSolenoid = new Solenoid(PneumaticsModuleType.REVPH, 1);
    public Solenoid topRightSolenoid = new Solenoid(PneumaticsModuleType.REVPH, 2);
    public Solenoid bottomLeftSolenoid = new Solenoid(PneumaticsModuleType.REVPH, 3);
    public Solenoid bottomRightSolenoid = new Solenoid(PneumaticsModuleType.REVPH, 4);
    public Solenoid brakeSolenoid = new Solenoid(PneumaticsModuleType.REVPH, 5);
    public Solenoid hookSolenoid = new Solenoid(PneumaticsModuleType.REVPH, 6);

    public ComponentsContainer() {
        leftDriveJoystick = new Joystick(0);
        rightDriveJoystick = new Joystick(1);
        manipulatorJoystick = new Joystick(2);

        // Ball System
        intakeLaserSensor = new DigitalInput(0);
        intakeMotor = new WPI_TalonSRX(21323);
        cargoTransferMotor = new WPI_TalonSRX(21334723);
        bottomShooterMotor = new WPI_TalonFX(321);
        topShooterMotor = new WPI_TalonFX(2487849);

        initDrive();
    }

    private void initDrive() {
        leftFrontDriveMotor = new WPI_TalonSRX(1);
        rightFrontDriveMotor = new WPI_TalonSRX(2);
        leftFollowerDriveMotor = new WPI_TalonSRX(3);
        rightFollowerDriveMotor = new WPI_TalonSRX(4);

        setSafeties(drive = new DifferentialDrive(leftFrontDriveMotor, rightFrontDriveMotor));

        setSafeties(leftFrontDriveMotor);
        setSafeties(rightFrontDriveMotor);
        setSafeties(leftFollowerDriveMotor);
        setSafeties(rightFollowerDriveMotor);

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
