package frc.robot;

import java.util.ArrayList;
import java.util.List;

public class AutoRunner {

    private List<AutoInstruction> concurrentInstructions = new ArrayList<AutoInstruction>();
    
    public void beginExecution(AutoInstruction instruction) {
        concurrentInstructions.add(instruction);
        instruction.init();
    }

    public void periodic() {
        concurrentInstructions.removeIf(AutoInstruction::hasCompleted);
        concurrentInstructions.forEach(AutoInstruction::periodic);
    }
}
