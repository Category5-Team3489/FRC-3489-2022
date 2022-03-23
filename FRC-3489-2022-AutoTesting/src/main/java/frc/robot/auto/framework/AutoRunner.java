package frc.robot.auto.framework;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import frc.robot.framework.RobotManager;

public class AutoRunner {

    private RobotManager robotManager;

    private final List<AutoInstruction> instructions = new ArrayList<AutoInstruction>();

    private final Map<String, AutoEvent> triggers = new HashMap<String, AutoEvent>();

    public AutoRunner(RobotManager robotManager) {
        this.robotManager = robotManager;
    }
    
    public void beginExecution(AutoInstruction instruction) {
        robotManager.copyReferences(instruction);
        instructions.add(instruction);
        instruction.begin();
        instruction.init();
        tryCompleteInstruction(instruction);
    }

    public void cancelExecution() {
        copyInstructions().forEach(AutoInstruction::completed);
        instructions.clear();
    }

    public void periodic() {
        checkComplete();
        copyInstructions().forEach(instruction -> {
            instruction.periodic();
            instruction.executePeriodicExtensions();
        });
    }

    public void fastPeriodic() {
        checkComplete();
        copyInstructions().forEach(AutoInstruction::fastPeriodic);
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
        if (event == null)
            return;
        event.run();
    }

    private void checkComplete() {
        copyInstructions().forEach(instruction -> tryCompleteInstruction(instruction));
    }

    private void tryCompleteInstruction(AutoInstruction instruction) {
        if (instruction.hasTimedOut())
            instruction.complete();
        if (!instruction.hasCompleted())
            return;
        instruction.completed();
        instruction.execute(nextInstruction -> beginExecution(nextInstruction));
        instructions.remove(instruction);
    }

    private List<AutoInstruction> copyInstructions() {
        return new ArrayList<AutoInstruction>(instructions);
    }
}
