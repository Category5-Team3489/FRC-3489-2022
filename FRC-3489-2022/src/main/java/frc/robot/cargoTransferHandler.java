public class cargoTransferHandler {
    ComponentsContainer container;
    Constants constants;
    public void transferUp(){
        container.cargoMoverMotor.set(constants.intakeSpeed);
    }
    public void transferDown(){
        container.cargoMoverMotor.set(-constants.intakeSpeed);
    }
    public void transferStop(){
        container.cargoMoverMotor.stopMotor();
    }
}
