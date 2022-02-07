package frc.robot;

public class CargoTransferHandler {
    ComponentsContainer container;
    Constants constants;
    public void transferUp(){
        container.cargoMoverMotor.set(Constants.intakeSpeed);
    }
    public void transferDown(){
        container.cargoMoverMotor.set(-Constants.intakeSpeed);
    }
    public void transferStop(){
        container.cargoMoverMotor.stopMotor();
    }
}
