package frc.robot.auto.framework;

import java.util.function.Consumer;

import frc.robot.auto.instructions.*;
import frc.robot.framework.RobotReferences;

public abstract class AutoInstruction extends RobotReferences {

    private boolean completed = false;

    protected final AutoEvent completedEvent = new AutoEvent();

    /**
     * Allows a runnable to be executed when the instruction completes
     * @param runnable Runnable to executed on completion
     * @return This instruction
     */
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

    /**
     * Allows this instruction to complete whenever the provided event is triggered
     * @param event The event that can trigger completion
     * @return This instruction
     */
    public final AutoInstruction completeOn(AutoEvent event) {
        event.sub(() -> complete());
        return this;
    }

    private AutoInstruction next = null;

    /**
     * Creates a blank instruction
     * Used internally
     * @param completeOnInit Complete the blank instruction on init
     * @return A blank instruction
     */
    public final BlankInstruction blank(boolean completeOnInit) {
        return setNext(AutoBuilder.blank(completeOnInit));
    }
    /**
     * Creates a drive instruction
     * @param clicks Clicks to drive
     * @return A drive instruction
     */
    public final DriveInstruction drive(double speed, double clicks) {
        return setNext(AutoBuilder.drive(speed, clicks));
    }
    /**
     * Creates a pause instruction
     * @param seconds Seconds to pause for
     * @return A pause instruction
     */
    public final PauseInstruction pause(double seconds) {
        return setNext(AutoBuilder.pause(seconds));
    }
    /**
     * Concurrently runs a set of instructions
     * @param concurrentInstructions Set of concurrent instructions
     * @return A concurrent instruction that represents the execution of the set
     */
    public final ConcurrentInstruction concurrently(AutoInstruction... concurrentInstructions) {
        return setNext(AutoBuilder.concurrently(concurrentInstructions));
    }
    public final CargoTransferInstruction cargoTransfer(double speed, double clicks) {
        return setNext(AutoBuilder.cargoTransfer(speed, clicks));
    }
    public final IntakeInstruction intake(double seconds) {
        return setNext(AutoBuilder.intake(seconds));
    }
    public final ShootInstruction shoot(double speed, double seconds) {
        return setNext(AutoBuilder.shoot(speed, seconds));
    }
    public final TurnInstruction turn(double speed, double degrees) {
        return setNext(AutoBuilder.turn(speed, degrees));
    }

    /**
     * Asynchronously runs a set of instructions
     * @param asyncInstructions Set of async instructions
     * @return A blank instruction after instantly starting the set
     */
    public final AutoInstruction asynchronously(AutoInstruction... asyncInstructions) {
        for (AutoInstruction instruction : asyncInstructions) {
            onCompleted(() -> autoHandler.runner.beginExecution(instruction));
        }
        return this;
    }
    /**
     * Prints a message
     * @param message The message
     * @return A blank instruction that completes instantly after printing
     */
    public final AutoInstruction print(String message) {
        onCompleted(() -> System.out.println(message));
        return this;
    }
    /**
     * Creates a blank instruction that completes when the provided event is triggered
     * @param event The event to wait for
     * @return A blank instruction representing the completion of the provided event
     */
    public final AutoInstruction waitUntil(AutoEvent event) {
        return AutoBuilder.waitUntil(event);
    }

    public final void execute(Consumer<AutoInstruction> executor) {
        if (next == null) return;
        executor.accept(next);
    }

    public boolean anyBelowIncomplete() {
        if (next == null) return false;
        if (!next.hasCompleted()) return true;
        if (next.anyBelowIncomplete()) return true;
        return false;
    }

    public abstract void init();

    public abstract void periodic();

    public void fastPeriodic() {}

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
