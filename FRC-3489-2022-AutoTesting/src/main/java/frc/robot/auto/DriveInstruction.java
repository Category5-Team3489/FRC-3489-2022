package frc.robot.auto;

import frc.robot.auto.framework.AutoInstruction;

public class DriveInstruction extends AutoInstruction {

    public DriveInstruction(double clicks) {
        
    }

    @Override
    public void init() {

    }

    @Override
    public void periodic() {
        complete();
    }

    @Override
    public String debug() {
        return "Drive Instruction: ";
    }

}
