package frc.robot.auto.instructions;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.FollowerType;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.Constants.Auto;
import frc.robot.auto.framework.AutoInstruction;
import frc.robot.utils.CSVUtils;

public class DriveInstruction extends AutoInstruction {

    private final static double kP = 0.15;
    private final static double kI = 0;//0.001;//0.0005;//0.001;
    private final static double kD = 2;//2;//5;
    private final static double kF = 0;
    private final static double Iz = 750; // required error to reset I accumulator

    private double speed;
    private double clicks;

    private Timer timer;

    public DriveInstruction(double speed, double clicks) {
        this.speed = speed;
        this.clicks = clicks;
    }

    @Override
    public void init() {
        components.setPIDStraightDrive();
        components.leftFrontDriveMotor.setSensorPhase(true);

        components.leftFrontDriveMotor.configNominalOutputForward(0, 30);
		components.leftFrontDriveMotor.configNominalOutputReverse(0, 30);
		components.leftFrontDriveMotor.configPeakOutputForward(speed, 30);
		components.leftFrontDriveMotor.configPeakOutputReverse(-speed, 30);

        components.leftFrontDriveMotor.configAllowableClosedloopError(0, 0, 30);

        components.leftFrontDriveMotor.config_kF(0, kF, 30);
		components.leftFrontDriveMotor.config_kP(0, kP, 30);
		components.leftFrontDriveMotor.config_kI(0, kI, 30);
		components.leftFrontDriveMotor.config_kD(0, kD, 30);
        components.leftFrontDriveMotor.config_IntegralZone(0, Iz);

        components.leftFrontDriveMotor.setSelectedSensorPosition(0, 0, 30);

        timer = new Timer();
        timer.start();
        CSVUtils.setColumns("test.csv", "Time,Category,Value,Notes");

        addNote(kF);
        addNote(kP);
        addNote(kI);
        addNote(kD);
        addNote(Iz);
        addNote(clicks);
    }

    @Override
    public void periodic() {
        components.leftFrontDriveMotor.set(ControlMode.Position, clicks);
        System.out.println("Speed: " + components.leftFrontDriveMotor.getMotorOutputPercent());
        System.out.println("Error: " + components.leftFrontDriveMotor.getClosedLoopError() / Auto.DriveClicksPerInch);
        addValue("CP100ms", components.leftFrontDriveMotor.getSelectedSensorVelocity());
        addValue("Error", components.leftFrontDriveMotor.getClosedLoopError());
        //complete();
    }

    @Override
    public void completed() {
        components.setDefaultDrive();
    }

    @Override
    public String debug() {
        return getInstructionName();
    }

    private void addValue(String category, double value) {
        CSVUtils.add("test.csv", timer.get() + "," + category + "," + value + ",0");
    }

    private void addNote(double note) {
        CSVUtils.add("test.csv", "0,Notes,0," + note);
    }

}
