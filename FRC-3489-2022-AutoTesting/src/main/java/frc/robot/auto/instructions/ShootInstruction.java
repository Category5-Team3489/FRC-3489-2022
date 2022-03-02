package frc.robot.auto.instructions;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.auto.framework.AutoInstruction;

public class ShootInstruction extends AutoInstruction {
    private Timer timer = new Timer();
    private double shootPeriod;
    private double shootSpeed; 

    public ShootInstruction(double speed, double seconds) {
        shootPeriod = seconds;
        shootSpeed = speed; 
    }

    @Override
    public void init() {
        timer.start();
    }

    @Override
    public void periodic() {
        shooterHandler.setShooter(shootSpeed, shootSpeed);
        if (timer.hasElapsed(shootPeriod))
            complete();

    }

    @Override
    public void completed() {
        shooterHandler.stopShooter();
    }

    @Override
    public String debug() {
        return getInstructionName();
    }
    
}
