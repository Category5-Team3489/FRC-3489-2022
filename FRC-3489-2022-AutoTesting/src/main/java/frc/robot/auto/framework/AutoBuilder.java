package frc.robot.auto.framework;

import frc.robot.auto.DriveInstruction;
import frc.robot.auto.PauseInstruction;
import frc.robot.auto.PrintInstruction;

public abstract class AutoBuilder {

    private AutoRunner runner;

    public final DriveInstruction drive(double clicks) {
        return (DriveInstruction)execute(new DriveInstruction(clicks));
    }

    public final PauseInstruction pause(double seconds) {
        return (PauseInstruction)execute(new PauseInstruction(seconds));
    }

    public final PrintInstruction print(String message) {
        return (PrintInstruction)execute(new PrintInstruction(message));
    }

    public final void setRunner(AutoRunner runner) {
        this.runner = runner;
    }

    public final AutoInstruction execute(AutoInstruction instruction) {
        runner.beginExecution(instruction);
        return instruction;
    }

    public final Runnable load(AutoInstruction instruction) {
        return () -> {
            runner.beginExecution(instruction);
        };
    }

    public final Runnable load(AutoInstruction instruction1, AutoInstruction instruction2) {
        return () -> {
            runner.beginExecution(instruction1);
            runner.beginExecution(instruction2);
        };
    }

    public final Runnable load(AutoInstruction instruction1, AutoInstruction instruction2, AutoInstruction instruction3) {
        return () -> {
            runner.beginExecution(instruction1);
            runner.beginExecution(instruction2);
            runner.beginExecution(instruction3);
        };
    }

    public final Runnable load(AutoInstruction instruction1, AutoInstruction instruction2, AutoInstruction instruction3, AutoInstruction instruction4) {
        return () -> {
            runner.beginExecution(instruction1);
            runner.beginExecution(instruction2);
            runner.beginExecution(instruction3);
            runner.beginExecution(instruction4);
        };
    }

    public final Runnable load(AutoInstruction instruction1, AutoInstruction instruction2, AutoInstruction instruction3, AutoInstruction instruction4, AutoInstruction instruction5) {
        return () -> {
            runner.beginExecution(instruction1);
            runner.beginExecution(instruction2);
            runner.beginExecution(instruction3);
            runner.beginExecution(instruction4);
            runner.beginExecution(instruction5);
        };
    }

    public abstract void build();

}
