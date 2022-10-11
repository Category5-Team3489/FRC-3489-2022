package frc.robot.framework.auto;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.framework.RobotReferences;

public abstract class AutoInstruction extends RobotReferences {
    private final AutoEvent initializedEvent = new AutoEvent();
    private final AutoEvent completedEvent = new AutoEvent();
    private final List<Supplier<Boolean>> periodicExtensions = new ArrayList<Supplier<Boolean>>();
    private final List<Supplier<Boolean>> fastPeriodicExtensions = new ArrayList<Supplier<Boolean>>();

    public final Timer timer = new Timer();

    private boolean hasCompleted = false;
    private AutoInstruction nextInstruction = null;
    private double timeout = Double.MAX_VALUE;

    // Life cycle
    public final void begin() {
        timer.start();
        initializedEvent.run();
    }
    public final void complete() {
        hasCompleted = true;
        completedEvent.run();
    }

    // Life cycle checks
    public final boolean hasCompleted() {
        return hasCompleted;
    }
    public final boolean hasTimedOut() {
        return timer.hasElapsed(timeout);
    }

    // Life cycle events
    public final AutoInstruction onInitialized(Runnable runnable) {
        initializedEvent.add(runnable);
        return this;
    }
    public final AutoInstruction onPeriodic(Supplier<Boolean> periodicExtension) {
        periodicExtensions.add(periodicExtension);
        return this;
    }
    public final AutoInstruction onFastPeriodic(Supplier<Boolean> fastPeriodicExtension) {
        fastPeriodicExtensions.add(fastPeriodicExtension);
        return this;
    }
    public final AutoInstruction onCompleted(Runnable runnable) {
        completedEvent.add(runnable);
        return this;
    }

    // Life cycle completion events
    public final AutoInstruction completeOn(AutoEvent event) {
        event.add(() -> complete());
        return this;
    }
    public final AutoInstruction timeoutAfter(double timeout) {
        if (this.timeout > timeout) {
            this.timeout = timeout;
        }
        return this;
    }

    // Instructions

    // Dummy instructions
    public final AutoInstruction asynchronously(AutoInstruction... asyncInstructions) {
        for (AutoInstruction instruction : asyncInstructions) {
            //onCompleted(() -> autoHandler.runner.beginExecution(instruction));
        }
        return this;
    }
    public final AutoInstruction print(String message) {
        onCompleted(() -> System.out.println(message));
        return this;
    }

    // TODO create "as" method, converts type of auto instruction to correct type
}
