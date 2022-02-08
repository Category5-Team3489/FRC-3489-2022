package frc.robot.auto.instructions;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.auto.framework.AutoInstruction;

public class RightInstruction extends AutoInstruction {

    private Timer timer = new Timer();
    private double speed;
    private double seconds;

    public RightInstruction(double speed, double seconds) {
        this.speed = speed;
        this.seconds = seconds;
    }

    @Override
    public void init() {
        timer.start();
    }

    @Override
    public void periodic() {
        if (timer.hasElapsed(seconds))
            complete();
        else
            components.rightTestMotor.set(speed);
    }

    @Override
    public void completed() {
        components.rightTestMotor.stopMotor();
    }

    @Override
    public String debug() {
        return getInstructionName();
    }
    
}
