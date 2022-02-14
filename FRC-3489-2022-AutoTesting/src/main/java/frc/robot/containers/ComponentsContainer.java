package frc.robot.containers;

import com.ctre.phoenix.motorcontrol.FollowerType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.Constants;

public final class ComponentsContainer {
    
    // Testing
    public WPI_TalonFX leftTestMotor;
    public WPI_TalonFX rightTestMotor;

    

    // Joysticks
    public Joystick leftDriveJoystick;
    public Joystick rightDriveJoystick;

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

        switch (Constants.SelectedRobot) {
            case RobotInABox:
                setSafeties(leftTestMotor = new WPI_TalonFX(6));
                setSafeties(rightTestMotor = new WPI_TalonFX(9));
                break;
            case IonV:
                initDrive();
                break;
            case Robot:
                leftDriveJoystick = new Joystick(0);
                rightDriveJoystick = new Joystick(1);
                initDrive();
                break;
        }
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
