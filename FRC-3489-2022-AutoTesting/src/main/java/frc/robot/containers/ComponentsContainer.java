package frc.robot.containers;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.Constants;

public final class ComponentsContainer {
    
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

    public ComponentsContainer() {

        if (Constants.IsRobotInABox) {
            setSafeties(leftTestMotor = new WPI_TalonFX(6));
            setSafeties(rightTestMotor = new WPI_TalonFX(9));
            return;
        }

        leftDriveJoystick = new Joystick(0);
        rightDriveJoystick = new Joystick(1);

        leftFrontDriveMotor = new WPI_TalonSRX(1);
        rightFrontDriveMotor = new WPI_TalonSRX(2);
        leftFollowerDriveMotor = new WPI_TalonSRX(3);
        rightFollowerDriveMotor = new WPI_TalonSRX(4);

        setSafeties(drive = new DifferentialDrive(leftFrontDriveMotor, rightFrontDriveMotor));

        setSafeties(leftFrontDriveMotor);
        setSafeties(rightFrontDriveMotor);
        setSafeties(leftFollowerDriveMotor);
        setSafeties(rightFollowerDriveMotor);

        rightFrontDriveMotor.setInverted(true);
        rightFollowerDriveMotor.setInverted(true);
        leftFollowerDriveMotor.follow(leftFrontDriveMotor);
        rightFollowerDriveMotor.follow(rightFrontDriveMotor);
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
