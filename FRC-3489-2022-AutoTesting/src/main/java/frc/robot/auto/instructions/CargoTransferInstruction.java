package frc.robot.auto.instructions;

import frc.robot.auto.framework.AutoInstruction;

public class CargoTransferInstruction extends AutoInstruction {

    private double cargoTransferSpeed;
    private double cargoTransferClicks;

    private double cargoTransferClicksOffset = 0;

    public CargoTransferInstruction(double speed, double clicks) {
        cargoTransferSpeed = speed;
        cargoTransferClicks = clicks;
    }

    @Override
    public void init() {
        cargoTransferClicksOffset = components.cargoTransferMotor.getSelectedSensorPosition();
    }

    @Override
    public void periodic() {
        double encoderClicks = Math.abs(components.cargoTransferMotor.getSelectedSensorPosition() - cargoTransferClicksOffset);
        if (encoderClicks < cargoTransferClicks) { // Has not reached target
            cargoTransferHandler.set(-cargoTransferSpeed);
        }
        else { // Has reached target
            complete();
        }
    }

    @Override
    public void completed() {
        cargoTransferHandler.set(0);
    }

    @Override
    public String debug() {
        return getInstructionName();
    }
    
}
