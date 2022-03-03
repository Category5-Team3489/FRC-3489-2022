package frc.robot.auto.instructions;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.auto.framework.AutoInstruction;

public class IntakeInstruction extends AutoInstruction {
    
    private Timer timer = new Timer();
    private double intakePeriod;

    public IntakeInstruction(double seconds) {
        intakePeriod = seconds;
    }

    @Override
    public void init() {
        timer.start();
    }

    @Override
    public void periodic() {
        intakeHandler.startIntake();
        if (timer.hasElapsed(intakePeriod))
            complete();
    }

    @Override
    public void completed() {
        intakeHandler.stopIntake();
    }

    @Override
    public String debug() {
        return getInstructionName();
    }
    
}
