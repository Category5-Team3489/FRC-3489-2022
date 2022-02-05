package frc.robot.auto.framework;

import frc.robot.auto.BeginInstruction;
import frc.robot.auto.DriveInstruction;
import frc.robot.auto.PauseInstruction;
import frc.robot.auto.PrintInstruction;

public abstract class AutoBuilder {

    private AutoRunner runner;

    public final void setRunner(AutoRunner runner) {
        this.runner = runner;
    }

    public final BeginInstruction begin() {
        BeginInstruction instruction = new BeginInstruction();
        runner.beginExecution(instruction);
        return instruction;
    }

    public final DriveInstruction drive(double clicks) {
        return new DriveInstruction(clicks);
    }
    public final PauseInstruction pause(double seconds) {
        return new PauseInstruction(seconds);
    }
    public final PrintInstruction print(String message) {
        return new PrintInstruction(message);
    }

    public abstract void build();

}
