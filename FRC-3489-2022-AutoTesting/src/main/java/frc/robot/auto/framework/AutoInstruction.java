package frc.robot.auto.framework;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import frc.robot.auto.DriveInstruction;
import frc.robot.auto.PauseInstruction;
import frc.robot.auto.PrintInstruction;
import frc.robot.framework.RobotReferences;

public abstract class AutoInstruction extends RobotReferences {

    private boolean completed = false;

    protected final AutoEvent completedEvent = new AutoEvent();

    public final AutoInstruction onCompleted(Runnable runnable) {
        completedEvent.sub(runnable);
        return this;
    }

    protected final void complete() {
        completed = true;
        completedEvent.run();
    }

    public final boolean hasCompleted() {
        return completed;
    }

    private List<AutoInstruction> instructions = new ArrayList<AutoInstruction>();

    public final DriveInstruction drive(double clicks) {
        DriveInstruction instruction = AutoBuilder.drive(clicks);
        instructions.add(instruction);
        return instruction;
    }
    public final PauseInstruction pause(double seconds) {
        PauseInstruction instruction = AutoBuilder.pause(seconds);
        instructions.add(instruction);
        return instruction;
    }
    public final PrintInstruction print(String message) {
        PrintInstruction instruction = AutoBuilder.print(message);
        instructions.add(instruction);
        return instruction;
    }

    public final AutoInstruction concurrently(AutoInstruction... concurrentInstructions) {
        instructions.addAll(Arrays.asList(concurrentInstructions));
        return this;
    }

    public final void execute(Consumer<AutoInstruction> beginExecution) {
        instructions.forEach(beginExecution);
    }

    public abstract void init();

    public abstract void periodic();

    public abstract String debug();

    protected final String getInstructionName() { 
        return getClass().getSimpleName();
    };

}
