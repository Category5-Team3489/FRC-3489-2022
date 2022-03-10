package frc.robot.auto.instructions;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.auto.framework.AutoInstruction;

public class DriveSecondsInstruction extends AutoInstruction {

    private double speed;
    private double seconds;

    private Timer timer = new Timer();

    public DriveSecondsInstruction(double speed, double seconds) {
        this.speed = speed;
        this.seconds = seconds;
    }

    @Override
    public void init() {
        timer.start();
    }

    @Override
    public void periodic() {
        components.drive.tankDrive(speed, speed);
        if (timer.hasElapsed(seconds))
            complete();
    }

    @Override
    public void completed() {
        components.drive.stopMotor();
    }

    @Override
    public String debug() {
        return null;
    }
    
}
