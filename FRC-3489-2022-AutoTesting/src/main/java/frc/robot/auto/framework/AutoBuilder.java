package frc.robot.auto.framework;

import frc.robot.auto.instructions.BlankInstruction;
import frc.robot.auto.instructions.ConcurrentInstruction;
import frc.robot.auto.instructions.DriveInstruction;
import frc.robot.auto.instructions.PauseInstruction;
import frc.robot.framework.RobotReferences;

public abstract class AutoBuilder extends RobotReferences {

    private AutoRunner runner;

    public final void setRunner(AutoRunner runner) {
        this.runner = runner;
    }

    public final AutoEvent signal(String signal) {
        return runner.signal(signal);
    }
    public final Runnable setSignal(String signal) {
        return (() -> runner.setSignal(signal));
    }

    public static final AutoInstruction head() {
        return new blank(true);
    }

    public final void begin(AutoInstruction head) {
        runner.beginExecution(head);
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

    public static final AutoInstruction waitOne(AutoEvent event) {
        return blank(true).completeOn(event);
    }

    public abstract void build();

}
