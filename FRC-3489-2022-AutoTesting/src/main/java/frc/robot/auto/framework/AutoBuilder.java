package frc.robot.auto.framework;

import frc.robot.auto.BeginInstruction;
import frc.robot.auto.DriveInstruction;
import frc.robot.auto.PauseInstruction;
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

    public final BeginInstruction begin() {
        BeginInstruction instruction = new BeginInstruction();
        runner.beginExecution(instruction);
        return instruction;
    }

    public static final BeginInstruction blank() {
        return new BeginInstruction();
    }
    public static final DriveInstruction drive(double clicks) {
        return new DriveInstruction(clicks);
    }
    public static final PauseInstruction pause(double seconds) {
        return new PauseInstruction(seconds);
    }
    public static final BeginInstruction print(String message) {
        BeginInstruction blank = blank();
        blank.print(message);
        return blank;
    }

    public abstract void build();

}
