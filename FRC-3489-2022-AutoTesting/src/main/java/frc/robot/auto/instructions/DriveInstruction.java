package frc.robot.auto.instructions;

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
    public void completed() {
        
    }

    @Override
    public String debug() {
        return getInstructionName();
    }

}
