package frc.robot.auto.instructions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import frc.robot.auto.framework.AutoInstruction;

public class ConcurrentInstruction extends AutoInstruction {

    protected List<AutoInstruction> concurrentInstructions = new ArrayList<AutoInstruction>();

    public ConcurrentInstruction(AutoInstruction... concurrentInstructions) {
        this.concurrentInstructions.addAll(Arrays.asList(concurrentInstructions));
    }

    @Override
    public void init() {
        concurrentInstructions.forEach(instruction -> autoHandler.runner.beginExecution(instruction));
        completeIfAll();
    }

    @Override
    public void periodic() {
        completeIfAll();
    }

    @Override
    public void completed() {
        
    }

    @Override
    public String debug() {
        return getInstructionName();
    }

    @Override
    public boolean anySequentialIncomplete() {
        for (AutoInstruction instruction : concurrentInstructions) {
            if (!instruction.hasCompleted() || instruction.anySequentialIncomplete())
                return true;
        }
        return false;
    }

    private void completeIfAll() {
        if (anySequentialIncomplete())
            return;
        complete();
    }
    
}
