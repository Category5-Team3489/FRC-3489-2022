package frc.robot.containers;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public final class ComponentsContainer {
    
    public WPI_TalonSRX leftTestMotor;// = new WPI_TalonSRX(6);
    public WPI_TalonSRX rightTestMotor;// = new WPI_TalonSRX(9);

    // Joysticks
    public Joystick leftDriveJoystick = new Joystick(0);
    public Joystick rightDriveJoystick = new Joystick(1);

    // Drive
    public WPI_TalonSRX leftFrontDriveMotor = new WPI_TalonSRX(1);
    public WPI_TalonSRX rightFrontDriveMotor = new WPI_TalonSRX(2);
    public WPI_TalonSRX leftFollowerDriveMotor = new WPI_TalonSRX(3);
    public WPI_TalonSRX rightFollowerDriveMotor = new WPI_TalonSRX(4);
    public DifferentialDrive drive;

    public ComponentsContainer() {
        //leftTestMotor.setSafetyEnabled(false);
        //rightTestMotor.setSafetyEnabled(false);

        rightFrontDriveMotor.setInverted(true);
        leftFollowerDriveMotor.follow(leftFrontDriveMotor);
        rightFollowerDriveMotor.follow(rightFrontDriveMotor);
        drive = new DifferentialDrive(leftFrontDriveMotor, rightFrontDriveMotor);
    }
}
