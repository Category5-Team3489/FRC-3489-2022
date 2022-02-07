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
        return autoHandler.autoRunner.signal(signal);
    }
    public final Runnable setSignal(String signal) {
        return (() -> autoHandler.autoRunner.setSignal(signal));
    }

    public final BlankInstruction begin() {
        BlankInstruction instruction = blank(true);
        runner.beginExecution(instruction);
        return instruction;
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

    public static final BlankInstruction print(String message) {
        BlankInstruction blank = blank(true);
        blank.print(message);
        return blank;
    }

    public static final AutoInstruction waitOne(AutoEvent event) {
        return blank(true).completeOn(event);
    }

    public abstract void build();

}
