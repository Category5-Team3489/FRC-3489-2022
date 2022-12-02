package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.OnBoardIO;

public class SetLED  extends CommandBase{
    private final double time;
    private OnBoardIO onBoardIO;
    private final Timer timer = new Timer();

    public SetLED(OnBoardIO onBoardIO, double time) {
        this.time = time;
        this.onBoardIO = onBoardIO;
        addRequirements(onBoardIO);

    }

    // init
    //turn on all LEDs
    @Override
    public void initialize() {
        timer.start();
        onBoardIO.setYellowLed(true);
    }

    //ex
    //none

    //is finished
    //reached time input
    @Override
    public boolean isFinished() {
        double currentTime = timer.get();
        if (currentTime > time) {
            return true;
        }
        return false;
    }

    //end
    // turn off LEDs
    @Override
    public void end(boolean interrupted) {
        onBoardIO.setYellowLed(false);
    }
}
