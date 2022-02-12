package frc.robot;

import frc.robot.framework.RobotHandler;

public class CargoTransferHandler extends RobotHandler {
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
