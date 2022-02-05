package frc.robot.auto.framework;

import frc.robot.auto.BeginInstruction;

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

    public abstract void build();

}
