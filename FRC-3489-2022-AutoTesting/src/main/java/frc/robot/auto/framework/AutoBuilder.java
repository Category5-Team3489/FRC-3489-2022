package frc.robot.auto.framework;

import frc.robot.auto.instructions.*;
import frc.robot.framework.RobotReferences;

public abstract class AutoBuilder extends RobotReferences {

    private AutoRunner runner;

    /**
     * Used to set the runner of the auto, used internally
     * @param runner
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

    public static final AutoInstruction first() {
        return blank(true);
    }

    public final void begin(AutoInstruction first) {
        runner.beginExecution(first);
    }

    public static final BlankInstruction blank(boolean completeOnInit) {
        return new BlankInstruction(completeOnInit);
    }
    public static final DriveInstruction drive(double clicks) {
        return new DriveInstruction(clicks);
    }
    public static final PauseInstruction pause(double seconds) {
        return new PauseInstruction(seconds);
    }
    public static final ConcurrentInstruction concurrently(AutoInstruction... concurrentInstructions) {
        return new ConcurrentInstruction(concurrentInstructions);
    }
    public static final LeftInstruction left(double speed, double seconds) {
        return new LeftInstruction(speed, seconds);
    }
    public static final RightInstruction right(double speed, double seconds) {
        return new RightInstruction(speed, seconds);
    }

    public static final AutoInstruction asynchronously(AutoInstruction... asyncInstructions) {
        return blank(true).asynchronously(asyncInstructions);
    }
    public static final AutoInstruction print(String message) {
        return blank(true).print(message);
    }

    public static final AutoInstruction waitUntil(AutoEvent event) {
        return blank(true).completeOn(event);
    }

    public abstract AutoInstruction build();

}
