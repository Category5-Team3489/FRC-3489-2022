package frc.robot.auto.instructions;

import com.revrobotics.RelativeEncoder;

//import com.ctre.phoenix.motorcontrol.ControlMode;

//import edu.wpi.first.wpilibj.Timer;
import frc.robot.auto.framework.AutoInstruction;
//import frc.robot.utils.CSVUtils;

public class DriveInstruction extends AutoInstruction {

    /*
    private final static double kP = 0.11;
    private final static double kI = 0.0001;//0.001;//0.0005;//0.001;
    private final static double kD = 25;//2;//5;
    private final static double kF = 0;
    private final static double Iz = 750; // required error to reset I accumulator

    private Timer timer;
    */

    private double speed;
    private double clicks;

    private RelativeEncoder encoder;

    public DriveInstruction(double speed, double clicks) {
        this.speed = speed;
        this.clicks = clicks;
    }

    @Override
    public void init() {
        //components.configAutoPIDDrive(Constants.MotorControllerTimeout, speed, kF, kP, kI, kD, Iz);

        /*
        timer = new Timer();
        timer.start();
        CSVUtils.setColumns("test.csv", "Time,Category,Value,Notes");

        addNote(kF);
        addNote(kP);
        addNote(kI);
        addNote(kD);
        addNote(Iz);
        addNote(clicks);
        */

        components.drive.tankDrive(speed, speed);
        encoder = components.leftFrontDriveMotor.getEncoder();
        encoder.setPosition(0);
    }

    @Override
    public void periodic() {
        //components.leftFrontDriveMotor.set(ControlMode.Position, clicks);
        //System.out.println("Speed: " + components.leftFrontDriveMotor.getMotorOutputPercent());
        //System.out.println("Error: " + components.leftFrontDriveMotor.getClosedLoopError() / Auto.DriveClicksPerInch);
        //addValue("CP100ms", components.leftFrontDriveMotor.getSelectedSensorVelocity());
        //addValue("Error", components.leftFrontDriveMotor.getClosedLoopError());
        //complete();
        /*
        if (Math.abs(components.leftFollowerDriveMotor.getClosedLoopError()) < Constants.ClicksPerInchDriven * 4)
            complete();
        */
        //if (Math.abs(components.leftFollowerDriveMotor.getClosedLoopError()) < Constants.ClicksPerInchDriven * 4)
        System.out.println("F: (" + components.leftFrontDriveMotor.getEncoder().getPosition() + ", " + components.rightFrontDriveMotor.getEncoder().getPosition() + ")");
        System.out.println("B: (" + components.leftFollowerDriveMotor.getEncoder().getPosition() + ", " + components.rightFollowerDriveMotor.getEncoder().getPosition() + ")");
    }

    @Override
    public void completed() {
        //CSVUtils.write("test.csv", true);
        components.configNominalDrive();
    }

    @Override
    public String debug() {
        return getInstructionName();
    }

    /*
    private void addValue(String category, double value) {
        CSVUtils.add("test.csv", timer.get() + "," + category + "," + value + ",0");
    }
    */

    /*
    private void addNote(double note) {
        CSVUtils.add("test.csv", "0,Notes,0," + note);
    }
    */

}
