package frc.robot.containers;

public final class ComponentsContainer {
    
    public WPI_TalonSRX leftTestMotor = new WPI_TalonSRX(12);
    public WPI_TalonSRX rightTestMotor = new WPI_TalonSRX(13);

    public ComponentsContainer() {
        leftTestMotor.setSafetyEnabled(false);
        rightTestMotor.setSafetyEnabled(false);
    }
}
