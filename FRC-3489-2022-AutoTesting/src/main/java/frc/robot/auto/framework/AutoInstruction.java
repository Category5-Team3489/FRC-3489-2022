package frc.robot.auto.framework;

import java.util.function.Consumer;

import frc.robot.auto.instructions.BlankInstruction;
import frc.robot.auto.instructions.ConcurrentInstruction;
import frc.robot.auto.instructions.DriveInstruction;
import frc.robot.auto.instructions.PauseInstruction;
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

    public final AutoInstruction completeOn(AutoEvent event) {
        event.sub(() -> complete());
        return this;
    }

    private AutoInstruction next = null;

    public final BlankInstruction blank(boolean completeOnInit) {
        return AutoBuilder.blank(completeOnInit);
    }
    public final DriveInstruction drive(double clicks) {
        return setNext(AutoBuilder.drive(clicks));
    }
    public final PauseInstruction pause(double seconds) {
        return setNext(AutoBuilder.pause(seconds));
    }
    public final ConcurrentInstruction concurrently(AutoInstruction... concurrentInstructions) {
        return setNext(AutoBuilder.concurrently(concurrentInstructions));
    }
    public final LeftInstruction left(double speed, double seconds) {
        return setNext(AutoBuilder.left(speed, seconds));
    }
    public final RightInstruction right(double speed, double seconds) {
        return setNext(AutoBuilder.right(speed, seconds));
    }

    public final AutoInstruction asynchronously(AutoInstruction... asyncInstructions) {
        for (AutoInstruction instruction : asyncInstructions) {
            onCompleted(() -> autoHandler.runner.beginExecution(instruction));
        }
        return this;
    }
    public final AutoInstruction print(String message) {
        onCompleted(() -> System.out.println(message));
        return this;
    }

    public final AutoInstruction waitOne(AutoEvent event) {
        return AutoBuilder.waitOne(event);
    }

    public final void execute(Consumer<AutoInstruction> executor) {
        if (next == null) return;
        executor.accept(next);
    }

    public abstract void init();

    public abstract void periodic();

    public abstract void completed();

    public abstract String debug();

    protected final String getInstructionName() { 
        return getClass().getSimpleName();
    };

    private <T extends AutoInstruction> T setNext(T instruction) {
        next = instruction;
        return instruction;
    }

}
