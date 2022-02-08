package frc.robot.auto.framework;

import frc.robot.auto.instructions.*;
import frc.robot.framework.RobotReferences;

public abstract class AutoBuilder extends RobotReferences {

    private AutoRunner runner;

    /**
     * Sets the runner of the auto
     * Used internally
     * @param runner Runner of the auto
     */
    public final void setRunner(AutoRunner runner) {
        this.runner = runner;
    }

    /**
     * Gets an event that represents the provided trigger name
     * <pre>
     * {@code
     * // Waits until trigger "trigger name" has been set elsewhere
     * .waitUntil(getTrigger("trigger name"));
     * // Allows instruction "anyInstruction" to be forced to complete externally by trigger "trigger name"
     * .anyInstruction().completeOn(getTrigger("trigger name"));
     * }
     * </pre>
     * @param trigger Name of the trigger being retrieved
     * @return An event that represents the trigger that can be set
     */
    public final AutoEvent getTrigger(String trigger) {
        return runner.getTrigger(trigger);
    }
    /**
     * Sets a trigger that represents the provided trigger name
     * <pre>
     * {@code
     * // Sets a trigger "trigger name" when "pause(3)" completes
     * .pause(3).onCompleted(setTrigger("trigger name"));
     * }
     * </pre>
     * @param trigger Name of the trigger being set
     * @return A runnable that represents setting the trigger
     */
    public final Runnable setTrigger(String trigger) {
        return (() -> runner.setTrigger(trigger));
    }

    /**
     * Gets the first instruction of an auto sequence that all others will be chained from
     * <pre>
     * {@code
     * // Gets the first instruction to use in a sequence
     * AutoInstruction first = first();
     * first
     * .anyInstruction()
     * .anyInstruction();
     * return first;
     * }
     * </pre>
     * @return An instruction that represents the first instruction
     */
    public static final AutoInstruction first() {
        return blank(true);
    }

    /**
     * Begins the execution of an instruction immediately
     * Used internally
     * @param first Instruction being executed
     */
    public final void begin(AutoInstruction first) {
        runner.beginExecution(first);
    }

    /**
     * Creates a blank instruction
     * Used internally
     * @param completeOnInit Complete the blank instruction on init
     * @return A blank instruction
     */
    public static final BlankInstruction blank(boolean completeOnInit) {
        return new BlankInstruction(completeOnInit);
    }
    /**
     * Creates a drive instruction
     * @param clicks Clicks to drive
     * @return A drive instruction
     */
    public static final DriveInstruction drive(double clicks) {
        return new DriveInstruction(clicks);
    }
    /**
     * Creates a pause instruction
     * @param seconds Seconds to pause for
     * @return A pause instruction
     */
    public static final PauseInstruction pause(double seconds) {
        return new PauseInstruction(seconds);
    }
    /**
     * Concurrently runs a set of instructions
     * @param concurrentInstructions Set of concurrent instructions
     * @return A concurrent instruction that represents the execution of the set
     */
    public static final ConcurrentInstruction concurrently(AutoInstruction... concurrentInstructions) {
        return new ConcurrentInstruction(concurrentInstructions);
    }
    /**
     * Creates a test instruction
     * @param speed Speed test motor should spin at
     * @param seconds Seconds test motor should spin for
     * @return A test instruction
     */
    public static final LeftInstruction left(double speed, double seconds) {
        return new LeftInstruction(speed, seconds);
    }
    /**
     * Creates a test instruction
     * @param speed Speed test motor should spin at
     * @param seconds Seconds test motor should spin for
     * @return A test instruction
     */
    public static final RightInstruction right(double speed, double seconds) {
        return new RightInstruction(speed, seconds);
    }

    /**
     * Asynchronously runs a set of instructions
     * @param asyncInstructions Set of async instructions
     * @return A blank instruction after instantly starting the set
     */
    public static final AutoInstruction asynchronously(AutoInstruction... asyncInstructions) {
        return blank(true).asynchronously(asyncInstructions);
    }
    /**
     * Prints a message
     * @param message The message
     * @return A blank instruction that completes instantly after printing
     */
    public static final AutoInstruction print(String message) {
        return blank(true).print(message);
    }
    /**
     * Creates a blank instruction that completes when the provided event is triggered
     * @param event The event to wait for
     * @return A blank instruction representing the completion of the provided event
     */
    public static final AutoInstruction waitUntil(AutoEvent event) {
        return blank(true).completeOn(event);
    }

    /**
     * The method all auto code is put in
     * Used internally
     * @return The first instruction of an auto sequence
     */
    public abstract AutoInstruction build();

}
