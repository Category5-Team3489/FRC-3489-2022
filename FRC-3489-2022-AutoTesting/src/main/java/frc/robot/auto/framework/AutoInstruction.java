package frc.robot.auto.framework;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.auto.instructions.*;
import frc.robot.framework.RobotReferences;

public abstract class AutoInstruction extends RobotReferences {

    protected final AutoEvent initializedEvent = new AutoEvent();
    protected final AutoEvent completedEvent = new AutoEvent();
    public final Timer timer = new Timer();
    private final List<Supplier<Boolean>> periodicExtensions = new ArrayList<Supplier<Boolean>>();

    private boolean completed = false;
    private AutoInstruction next = null;
    private double timeout = Double.MAX_VALUE;

    public final void begin() {
        timer.start();
        initializedEvent.run();
    }

    public final void complete() {
        completed = true;
        completedEvent.run();
    }

    public final boolean hasCompleted() {
        return completed;
    }

    public final boolean hasTimedOut() {
        return timer.hasElapsed(timeout);
    }

    public final AutoInstruction onInitialized(Runnable runnable) {
        initializedEvent.sub(runnable);
        return this;
    }

    /**
     * Allows a runnable to be executed when the instruction completes
     * @param runnable Runnable to executed on completion
     * @return This instruction
     */
    public final AutoInstruction onCompleted(Runnable runnable) {
        completedEvent.sub(runnable);
        return this;
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

    public final AutoInstruction withTimeout(double timeout) {
        if (this.timeout > timeout) {
            this.timeout = timeout;
        }
        return this;
    }

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
    public final DriveSecondsInstruction driveSeconds(double speed, double seconds) {
        return setNext(AutoBuilder.driveSeconds(speed, seconds));
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
    public final AutoInstruction periodically(Supplier<Boolean> periodicExtension) {
        periodicExtensions.add(periodicExtension);
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

    public final void executeNext(Consumer<AutoInstruction> executor) {
        if (next == null)
            return;
        executor.accept(next);
    }

    public final void executePeriodicExtensions() {
        new ArrayList<Supplier<Boolean>>(periodicExtensions).forEach(periodicExtension -> {
            if (periodicExtension.get())
                complete();
        });
    }

    public boolean anySequentialIncomplete() {
        if (next == null)
            return false;
        if (!next.hasCompleted())
            return true;
        if (next.anySequentialIncomplete())
            return true;
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
