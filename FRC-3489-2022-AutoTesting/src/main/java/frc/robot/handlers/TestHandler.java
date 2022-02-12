package frc.robot.handlers;

import java.util.PriorityQueue;
import java.util.Queue;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.framework.RobotHandler;
import frc.robot.utils.CSVUtils;
import frc.robot.utils.GeneralUtils;

public class TestHandler extends RobotHandler {

    private Timer timer;

    private double lastPosition = 0;
    private static final double TargetRPS = 50;
    private double lastError = TargetRPS;

    private Queue<Double> rpsStack = new PriorityQueue<Double>();

    private double speed = 0;
    
    @Override
    public void teleopInit() {
        components.rightTestMotor.setSelectedSensorPosition(0);
        //components.rightTestMotor.config_kP(slotIdx, value)
        //components.rightTestMotor.set()
        //components.leftFrontDriveMotor.
        timer = new Timer();
        timer.start();
        CSVUtils.setColumns("test.csv", "Time,Category,Value");
    }

    @Override
    public void teleopPeriodic() {
        rpsStack.add(getRPS());
        while (rpsStack.size() > 5) {
            rpsStack.remove();
        }
        double rps = 0;
        for (double previousRps : rpsStack) {
            rps += previousRps;
        }
        rps /= rpsStack.size();
        double error = TargetRPS - rps;
        double p = error * -0.004;//0.004;
        double i = 0;
        //double d = (error - lastError) * 0.002;
        double d = 0;
        speed += p + i + d;
        components.rightTestMotor.set(speed);

        addValue("1. Error", error / 500);
        addValue("2. P", p);
        addValue("3. I", i);
        addValue("4. D", d);
        addValue("5. Speed", speed);

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
