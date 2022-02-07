package frc.robot.auto.instructions;

import frc.robot.auto.framework.AutoInstruction;

public class BlankInstruction extends AutoInstruction {

    private boolean completeOnInit;

    public BlankInstruction(boolean completeOnInit) {
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
    public void completed() {
        
    }

    @Override
    public String debug() {
        return getInstructionName();
    }
    
}
