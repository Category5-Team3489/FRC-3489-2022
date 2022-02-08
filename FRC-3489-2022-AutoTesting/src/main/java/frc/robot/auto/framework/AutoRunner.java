package frc.robot.auto.framework;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import frc.robot.framework.RobotManager;

public class AutoRunner {

    private RobotManager robotManager;

    private List<AutoInstruction> concurrentInstructions = new ArrayList<AutoInstruction>();

    private Map<String, AutoEvent> signals = new HashMap<String, AutoEvent>();

    public AutoRunner(RobotManager robotManager) {
        this.robotManager = robotManager;
    }
    
    public void beginExecution(AutoInstruction instruction) {
        robotManager.copyReferences(instruction);
        concurrentInstructions.add(instruction);
        instruction.init();
        completeInstruction(instruction);
    }

    public void periodic() {
        copyInstructions().forEach(instruction -> completeInstruction(instruction));
        copyInstructions().forEach(AutoInstruction::periodic);
    }

    public AutoEvent signal(String signal) {
        if (signals.containsKey(signal))
            return signals.get(signal);
        AutoEvent event = new AutoEvent();
        signals.put(signal, event);
        return event;
    }

    public void setSignal(String signal) {
        AutoEvent event = signals.get(signal);
        if (event == null) return;
        event.run();
    }

    private void completeInstruction(AutoInstruction instruction) {
        if (!instruction.hasCompleted()) return;
        instruction.completed();
        instruction.execute(nextInstruction -> beginExecution(nextInstruction));
        concurrentInstructions.remove(instruction);
    }

    private List<AutoInstruction> copyInstructions() {
        return new ArrayList<AutoInstruction>(concurrentInstructions);
    }
}
