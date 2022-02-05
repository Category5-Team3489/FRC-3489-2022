package frc.robot.auto;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.auto.framework.AutoInstruction;

public class PauseInstruction extends AutoInstruction {

    private Timer timer = new Timer();
    private double pausePeriod;

    public PauseInstruction(double seconds) {
        pausePeriod = seconds;
    }

    @Override
    public void init() {
        timer.start();
    }

    @Override
    public void periodic() {
        if (timer.hasElapsed(pausePeriod))
            complete();
    }

    @Override
    public String debug() {
        return "Pause Instruction: " + ((int)((pausePeriod - timer.get()) * 1000)) + "ms remaining";
    }
}
