package frc.robot.auto;

import frc.robot.auto.framework.AutoInstruction;

public class BeginInstruction extends AutoInstruction {

    private boolean completeOnInit = true;

    public BeginInstruction() {
        
    }

    public BeginInstruction(boolean completeOnInit) {
        this.completeOnInit = completeOnInit;
    }

    @Override
    public void init() {
        if (completeOnInit) complete();
    }

    @Override
    public void periodic() {
        
    }

    @Override
    public String debug() {
        return getInstructionName();
    }
    
}
