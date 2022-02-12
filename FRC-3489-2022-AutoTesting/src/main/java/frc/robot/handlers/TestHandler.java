package frc.robot.handlers;


import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.Constants;
import frc.robot.framework.RobotHandler;
import frc.robot.types.RobotType;
import frc.robot.utils.CSVUtils;

public class TestHandler extends RobotHandler {

    private Timer timer;

    //private double lastPosition = 0;
    //private double lastError = TargetRPS;

    //private Queue<Double> rpsStack = new LinkedList<Double>();

    //sprivate double speed = 0;

    private WPI_TalonFX talon;

    private static final double TargetRPS = 50;
    private static final int TimeoutMS = 30;

    private final static double kP = 0.1;
    private final static double kI = 0.0005;//0.001;//0.0005;//0.001;
    private final static double kD = 10;//2;//5;
    private final static double kF = 1023.0/20660.0;
    private final static double Iz = 750; // required error to reset I accumulator
    //private final static double PeakOutput = 1;
    
    @Override
    public void teleopInit() {
        if (Constants.SelectedRobot != RobotType.RobotInABox) return;

        talon = components.rightTestMotor;

        talon.configFactoryDefault();
        talon.configNeutralDeadband(0.001);
        talon.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, TimeoutMS);

        talon.configNominalOutputForward(0, TimeoutMS);
		talon.configNominalOutputReverse(0, TimeoutMS);
		talon.configPeakOutputForward(1, TimeoutMS);
		talon.configPeakOutputReverse(-1, TimeoutMS);

        talon.config_kF(0, kF, TimeoutMS);
		talon.config_kP(0, kP, TimeoutMS);
		talon.config_kI(0, kI, TimeoutMS);
		talon.config_kD(0, kD, TimeoutMS);

        talon.config_IntegralZone(0, Iz);

        timer = new Timer();
        timer.start();
        CSVUtils.setColumns("test.csv", "Time,Category,Value,Notes");
        addNote(kF);
        addNote(kP);
        addNote(kI);
        addNote(kD);
        addNote(Iz);
        addNote((TargetRPS * 2048.0) / 10.0);

    }

    @Override
    public void teleopPeriodic() {
        if (Constants.SelectedRobot != RobotType.RobotInABox) return;

        talon.set(TalonFXControlMode.Velocity, (TargetRPS * 2048.0) / 10.0);

        //addValue("RPS", (talon.getSelectedSensorVelocity() * 10.0) / 2048.0);
        addValue("CPS", talon.getSelectedSensorVelocity() * 10);
        addValue("Position", talon.getSelectedSensorPosition());
        //addValue("Error", talon.getClosedLoopError() / ((TargetRPS * 2048.0) / 10.0));
        //addValue("I", talon.getIntegralAccumulator());
        //addValue("D", talon.getErrorDerivative());
        //addValue("Output", talon.getMotorOutputPercent());


        /*
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
        */

    }

    @Override
    public void disabledInit() {
        if (Constants.SelectedRobot != RobotType.RobotInABox) return;
        if (timer == null) return;
        CSVUtils.write("test.csv", true);
    }

    /*
    private double getRPS() {
        double position = components.rightTestMotor.getSelectedSensorPosition();
        double rpTick = position - lastPosition;
        lastPosition = position;
        return rpTick / 50d;
        //return (components.leftTestMotor.getSelectedSensorVelocity() * 10) / 2048;
    }
    */

    private void addValue(String category, double value) {
        CSVUtils.add("test.csv", timer.get() + "," + category + "," + value + ",0");
    }

    private void addNote(double note) {
        CSVUtils.add("test.csv", "0,Notes,0," + note);
    }

}
