package frc.robot.auto.framework;

import java.util.ArrayList;
import java.util.List;

import frc.robot.framework.RobotManager;

public class AutoRunner {

    private RobotManager robotManager;

    private List<AutoInstruction> concurrentInstructions = new ArrayList<AutoInstruction>();

    public AutoRunner(RobotManager robotManager) {
        this.robotManager = robotManager;
    }
    
    public void beginExecution(AutoInstruction instruction) {
        robotManager.copyReferences(instruction);
        concurrentInstructions.add(instruction);
        instruction.init();
        checkInstruction(instruction);
    }

    public void periodic() {
        concurrentInstructions.forEach(instruction -> checkInstruction(instruction));
        concurrentInstructions.removeIf(AutoInstruction::hasCompleted);
    }

    private void checkInstruction(AutoInstruction instruction) {
        if (instruction.hasCompleted())
            instruction.execute(chainedInstruction -> beginExecution(chainedInstruction));
        else
            instruction.periodic();
    }
}
