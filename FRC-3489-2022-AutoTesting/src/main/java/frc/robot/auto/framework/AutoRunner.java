package frc.robot.auto.framework;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import frc.robot.framework.RobotManager;

public class AutoRunner {

    private RobotManager robotManager;

    private List<AutoInstruction> concurrentInstructions = new ArrayList<AutoInstruction>();

    private Map<String, AutoEvent> triggers = new HashMap<String, AutoEvent>();

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
        checkComplete();
        copyInstructions().forEach(AutoInstruction::periodic);
    }

    public void fastPeriodic() {
        checkComplete();
        copyInstructions().forEach(AutoInstruction::periodic);
    }

    public AutoEvent getTrigger(String trigger) {
        if (triggers.containsKey(trigger))
            return triggers.get(trigger);
        AutoEvent event = new AutoEvent();
        triggers.put(trigger, event);
        return event;
    }

    public void setTrigger(String trigger) {
        AutoEvent event = triggers.get(trigger);
        if (event == null) return;
        event.run();
    }

    private void checkComplete() {
        copyInstructions().forEach(instruction -> completeInstruction(instruction));
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
