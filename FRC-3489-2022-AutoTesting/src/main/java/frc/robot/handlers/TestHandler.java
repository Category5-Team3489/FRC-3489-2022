package frc.robot.handlers;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.framework.RobotHandler;
import frc.robot.utils.CSVUtils;
import frc.robot.utils.GeneralUtils;

public class TestHandler extends RobotHandler {

    private Timer timer;

    private double lastPosition = 0;
    private static final double TargetRPS = 50;
    private double lastError = TargetRPS;
    
    @Override
    public void teleopInit() {
        components.rightTestMotor.setSelectedSensorPosition(0);
        timer = new Timer();
        timer.start();
        CSVUtils.setColumns("test.csv", "Time,Category,Value");
    }

    @Override
    public void teleopPeriodic() {

        double rps = getRPS();
        double error = TargetRPS - rps;
        double p = GeneralUtils.clamp(error * -0.01, -0.3, 0.3);//0.004;
        double i = 0;
        //double d = (error - lastError) * 0.002;
        double d = 0;
        double speed = p + i + d;
        components.rightTestMotor.set(speed);

        addValue("1. Error", error / 50);
        addValue("2. P", p);
        addValue("3. I", i);
        addValue("4. D", d);
        addValue("5. Speed", speed);
        addValue("6. RPS", rps / 50);

        lastError = error;

    }

    @Override
    public void disabledInit() {
        if (timer == null) return;
        CSVUtils.write("test.csv", true);
    }

    private double getRPS() {
        double position = components.rightTestMotor.getSelectedSensorPosition();
        double rpTick = position - lastPosition;
        lastPosition = position;
        return rpTick / 50d;
        //return (components.leftTestMotor.getSelectedSensorVelocity() * 10) / 2048;
    }

    private void addValue(String category, double value) {
        CSVUtils.add("test.csv", timer.get() + "," + category + "," + value);
    }

}
