package frc.robot.containers;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.Constants;

public final class ComponentsContainer {
    
    public WPI_TalonSRX leftTestMotor;
    public WPI_TalonSRX rightTestMotor;

    // Joysticks
    public Joystick leftDriveJoystick;
    public Joystick rightDriveJoystick;

    // Drive
    public WPI_TalonSRX leftFrontDriveMotor;
    public WPI_TalonSRX rightFrontDriveMotor;
    public WPI_TalonSRX leftFollowerDriveMotor;
    public WPI_TalonSRX rightFollowerDriveMotor;
    public DifferentialDrive drive;

    public ComponentsContainer() {

        if (Constants.IsRobotInABox) {
            setSafeties(leftTestMotor = new WPI_TalonSRX(6));
            setSafeties(rightTestMotor = new WPI_TalonSRX(9));
            return;
        }

        leftDriveJoystick = new Joystick(0);
        rightDriveJoystick = new Joystick(1);

        setSafeties(leftFrontDriveMotor = new WPI_TalonSRX(1));
        setSafeties(rightFrontDriveMotor = new WPI_TalonSRX(2));
        setSafeties(leftFollowerDriveMotor = new WPI_TalonSRX(3));
        setSafeties(rightFollowerDriveMotor = new WPI_TalonSRX(4));

        rightFrontDriveMotor.setInverted(true);
        leftFollowerDriveMotor.follow(leftFrontDriveMotor);
        rightFollowerDriveMotor.follow(rightFrontDriveMotor);
        setSafeties(drive = new DifferentialDrive(leftFrontDriveMotor, rightFrontDriveMotor));
    }

    private void setSafeties(WPI_TalonSRX motor) {
        motor.setSafetyEnabled(Constants.SafetiesEnabled);
    }
    private void setSafeties(DifferentialDrive drive) {
        drive.setSafetyEnabled(Constants.SafetiesEnabled);
    }
}
