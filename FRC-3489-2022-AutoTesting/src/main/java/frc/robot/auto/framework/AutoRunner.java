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
        if (!completeInstruction(instruction)) return;
        concurrentInstructions.remove(instruction);
    }

    public void periodic() {
        concurrentInstructions.removeIf(instruction -> completeInstruction(instruction));
        concurrentInstructions.forEach(AutoInstruction::periodic);
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

    private boolean completeInstruction(AutoInstruction instruction) {
        if (!instruction.hasCompleted()) return false;
        instruction.completed();
        instruction.execute(nextInstruction -> beginExecution(nextInstruction));
        return true;
    }
}
