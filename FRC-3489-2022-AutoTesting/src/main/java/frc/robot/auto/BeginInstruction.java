package frc.robot.auto;

import frc.robot.auto.framework.AutoInstruction;

public class BeginInstruction extends AutoInstruction {

    @Override
    public void init() {
        complete();
    }

    @Override
    public void periodic() {
        
    }

    @Override
    public String debug() {
        return getInstructionName();
    }
    
}
