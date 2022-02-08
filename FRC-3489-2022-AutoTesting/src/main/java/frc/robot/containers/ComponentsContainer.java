package frc.robot.containers;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public final class ComponentsContainer {
    
    public WPI_TalonSRX leftTestMotor;// = new WPI_TalonSRX(6);
    public WPI_TalonSRX rightTestMotor;// = new WPI_TalonSRX(9);

    public WPI_TalonSRX leftFrontDriveMotor = new WPI_TalonSRX(1);
    public WPI_TalonSRX rightFrontDriveMotor = new WPI_TalonSRX(2);
    public WPI_TalonSRX leftFollowerDriveMotor = new WPI_TalonSRX(3);
    public WPI_TalonSRX rightFollowerDriveMotor = new WPI_TalonSRX(4);

    public ComponentsContainer() {
        //leftTestMotor.setSafetyEnabled(false);
        //rightTestMotor.setSafetyEnabled(false);

        rightFrontDriveMotor.setInverted(true);
        leftFollowerDriveMotor.follow(leftFrontDriveMotor);
        rightFollowerDriveMotor.follow(rightFrontDriveMotor);
    }
}
