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
        concurrentInstructions.forEach(instruction -> autoHandler.autoRunner.beginExecution(instruction));
        completeIfAll();
    }

    @Override
    public void periodic() {
        completeIfAll();
    }

    @Override
    public String debug() {
        return getInstructionName();
    }

    private boolean anyIncomplete() {
        for (AutoInstruction instruction : concurrentInstructions) {
            if (!instruction.hasCompleted()) return true;
            if (instruction instanceof ConcurrentInstruction) {
                ConcurrentInstruction concurrentInstruction = (ConcurrentInstruction)instruction;
                return concurrentInstruction.anyIncomplete();
            }
        }
        return false;
    }

    private void completeIfAll() {
        if (anyIncomplete()) return;
        complete();
    }
    
}
