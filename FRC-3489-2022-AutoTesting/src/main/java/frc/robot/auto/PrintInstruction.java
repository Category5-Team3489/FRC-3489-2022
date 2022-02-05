package frc.robot.auto;

import frc.robot.auto.framework.AutoInstruction;

public class PrintInstruction extends AutoInstruction {

    private String message;

    public PrintInstruction(String message) {
        this.message = message;
    }

    @Override
    public void init() {
        System.out.println(message);
    }

    @Override
    public void periodic() {
        complete();
    }

    @Override
    public String debug() {
        return "Print Instruction: " + message;
    }
}
