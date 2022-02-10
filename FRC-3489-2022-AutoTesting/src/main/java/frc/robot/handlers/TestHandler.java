package frc.robot.handlers;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.framework.RobotHandler;
import frc.robot.utils.FileUtils;

public class TestHandler extends RobotHandler {

    private String csv = "";
    private Timer timer;

    //private double lastPosition = 0;
    private static final double TargetRPS = 50;
    private double lastError = TargetRPS;
    
    @Override
    public void teleopInit() {
        components.rightTestMotor.setSelectedSensorPosition(0);
        timer = new Timer();
        timer.start();
        addLine("Time,Category,Value");
    }

    @Override
    public void teleopPeriodic() {

        double error = getError(TargetRPS);
        double p = error * 0.1;//0.004;
        double i = 0;
        //double d = (error - lastError) * 0.02 * 0;
        double d = 0;
        double speed = p + i + d;
        components.rightTestMotor.set(speed);

        addValue("Error", error / 50);
        addValue("P", p);
        addValue("I", i);
        addValue("D", d);
        addValue("Speed", speed);

        lastError = error;

    }

    @Override
    public void disabledInit() {
        if (timer == null) return;
        if (!FileUtils.fileExists("data.csv"))
            FileUtils.createLocalFile("data.csv");
        FileUtils.writeLocalFile("data.csv", csv);
        csv = "";
    }

    private double getError(double targetRPS) {
        return targetRPS - getRPS();
    }

    private double getRPS() {
        //double position = components.rightTestMotor.getSelectedSensorPosition();
        //double rpTick = position - lastPosition;
        //lastPosition = position;
        //return rpTick * 50d;
        return (components.rightTestMotor.getSelectedSensorVelocity() * 10) / 2048;
    }

    private void addValue(String category, double value) {
        addLine(timer.get() + "," + category + "," + value);
    }

    private void addLine(String line) {
        csv += line + "\n";
    }
}
