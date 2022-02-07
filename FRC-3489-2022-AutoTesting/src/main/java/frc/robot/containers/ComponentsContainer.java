package frc.robot.containers;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public final class ComponentsContainer {
    
    public WPI_TalonSRX leftTestMotor = new WPI_TalonSRX(6);
    public WPI_TalonSRX rightTestMotor = new WPI_TalonSRX(9);

    public ComponentsContainer() {
        leftTestMotor.setSafetyEnabled(false);
        rightTestMotor.setSafetyEnabled(false);
    }
}
