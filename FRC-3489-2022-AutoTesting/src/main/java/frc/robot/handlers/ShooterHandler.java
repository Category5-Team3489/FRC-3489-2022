package frc.robot.handlers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.Constants;
import frc.robot.framework.RobotHandler;
import frc.robot.interfaces.IShuffleboardState;
import frc.robot.types.DataPoint;
import frc.robot.types.ShooterSetting;
import frc.robot.types.ShooterState;
import frc.robot.utils.CSVUtils;
import frc.robot.utils.GeneralUtils;

public class ShooterHandler extends RobotHandler implements IShuffleboardState {

    private ShooterState shooterState = ShooterState.Disabled;

    private double currentBottomSpeed = 0;
    private double currentTopSpeed = 0;

    /*
    private final static double kP = 0.11;
    private final static double kI = 0.0001;//0.001;//0.0005;//0.001;
    private final static double kD = 25;//2;//5;
    private final static double kF = 0;
    private final static double Iz = 750; // required error to reset I accumulator
    */

    private int loop = 0;

    public void shootLow() {
        if (update(ShooterState.Low)) {
            setShooter(Constants.ShootLowBottomMotorSpeed, Constants.ShootLowTopMotorSpeed);
            setShuffleboardState();
        }
    }
    
    public void shootHigh() {
        if (update(ShooterState.High)) {
            setShooter(Constants.ShootHighBottomMotorSpeed, Constants.ShootHighTopMotorSpeed);
            setShuffleboardState();
        }
    }

    public void setWrongColor() {
        if (update(ShooterState.WrongColor)) {
            setShooter(Constants.WrongColorBottomSpeed, Constants.WrongColorTopSpeed);
            setShuffleboardState();
        }
    }

    public void setShooterAtDistance(double distance) {
        ShooterSetting setting = getShooterSetting(distance);
        components.bottomShooterMotor.set(ControlMode.Velocity, setting.bottomSpeed);
        components.topShooterMotor.set(ControlMode.Velocity, -setting.topSpeed);
        System.out.println(setting.bottomSpeed + ":::" + setting.topSpeed);
    }

    public void stop() {
        components.bottomShooterMotor.set(ControlMode.Velocity, 0);
        components.topShooterMotor.set(ControlMode.Velocity, 0);
        if (update(ShooterState.Disabled)) {
            //setShooter(0, 0);
            components.bottomShooterMotor.set(ControlMode.Velocity, 0);
            components.topShooterMotor.set(ControlMode.Velocity, 0);
            setShuffleboardState();
        }
    }

    public boolean canShoot() {
        return true;
        //return currentBottomSpeed > Constants.Shooter.CanShootSpeedThreshold && currentTopSpeed > Constants.Shooter.CanShootSpeedThreshold;
    }

    public void setShooter(ShooterSetting setting) {
        setShooter(setting.bottomSpeed, -setting.topSpeed);
        setShuffleboardState();
    }

    public void setShooter(double bottomSpeed, double topSpeed) {
        currentBottomSpeed = bottomSpeed;
        currentTopSpeed = topSpeed;
        components.bottomShooterMotor.set(bottomSpeed);
        components.topShooterMotor.set(-topSpeed);
    }

    public ShooterState getShooterState() {
        return shooterState;
    }

    @Override
    public void setShuffleboardState() {
        shuffleboardHandler.setString(true, "Shooter State", shooterState.toString());
    }

    private final static double kP = 0.125;
    private final static double kI = 0;//0.001;//0.001;//0.0005;//0.001;
    private final static double kD = 0;//5;//2;//5;
    private final static double kF = (0.5 * 1023.0) / 11250;
    private final static double Iz = 300; // required error to reset I accumulator

    private Timer timer;

    private void config(TalonFX _talon) {
        _talon.configFactoryDefault();
		
		/* Config neutral deadband to be the smallest possible */
		_talon.configNeutralDeadband(0.001);

		/* Config sensor used for Primary PID [Velocity] */
        _talon.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor,
                                            0, 
											30);
											

		/* Config the peak and nominal outputs */
		_talon.configNominalOutputForward(0, 30);
		_talon.configNominalOutputReverse(0, 30);
		_talon.configPeakOutputForward(1, 30);
		_talon.configPeakOutputReverse(-1, 30);

		/* Config the Velocity closed loop gains in slot0 */
		_talon.config_kF(0, kF, 30);
		_talon.config_kP(0, kP, 30);
		_talon.config_kI(0, kI, 30);
		_talon.config_kD(0, kD, 30);
    }

    @Override
    public void teleopInit() {

        config(components.bottomShooterMotor);
        config(components.topShooterMotor);

        timer = new Timer();
        timer.start();
        CSVUtils.setColumns("n.csv", "Time,Category,Value,Notes");

        addNote(kF);
        addNote(kP);
        addNote(kI);
        addNote(kD);
        addNote(Iz);
    }

    @Override
    public void teleopPeriodic() {
        //testShooterPeriodic();
        loop++;
    }

    @Override
    public void disabledInit() {
        CSVUtils.write("n.csv", true);
    }

    private boolean update(ShooterState desired) {
        if (shooterState != desired) {
            shooterState = desired;
            return true;
        }
        return false;
    }

    public static ShooterSetting getShooterSetting(double distance) {
        ArrayList<DataPoint> indicies = getClosestDistanceIndicies(distance);
        int a = indicies.get(0).x;
        int b = indicies.get(1).x;
        double d1 = Constants.Shooter.ShooterSpeedAtDistanceTable[a].y;
        double d2 = Constants.Shooter.ShooterSpeedAtDistanceTable[b].y;
        if (d2 < d1) {
            double d3 = d1;
            d1 = d2;
            d2 = d3;

            int c = a;
            a = b;
            b = c;
        }
        double t = (d2 - distance) / (d2 - d1);
        ShooterSetting settingA = new ShooterSetting(Constants.Shooter.BottomShooterSpeedAtDistanceTable[a], Constants.Shooter.TopShooterSpeedAtDistanceTable[a]);
        ShooterSetting settingB = new ShooterSetting(Constants.Shooter.BottomShooterSpeedAtDistanceTable[b], Constants.Shooter.TopShooterSpeedAtDistanceTable[b]);
        return new ShooterSetting(GeneralUtils.lerp(settingA.bottomSpeed, settingB.bottomSpeed, t), GeneralUtils.lerp(settingA.topSpeed, settingB.topSpeed, t));
    }

    public static ArrayList<DataPoint> getClosestDistanceIndicies(double distance) {
        ArrayList<DataPoint> indicies = new ArrayList<DataPoint>();
        for (DataPoint point : Constants.Shooter.ShooterSpeedAtDistanceTable) {
            indicies.add(DataPoint.c(point.x, point.y - distance));
        }
        indicies.sort((a, b) -> DataPoint.compare(a, b));
        return indicies;
    }

    /////////////////////////////////////////////
    ///////////////   TESTING    ////////////////
    /////////////////////////////////////////////
    private void testShooterPeriodic() {
        double setSpeedB = getSetSpeedBottom((components.leftDriveJoystick.getThrottle() + 1d) / 2);
        double setSpeedT = getSetSpeedTop((components.rightDriveJoystick.getThrottle() + 1d) / 2);

        double bVelocity = components.bottomShooterMotor.getSelectedSensorVelocity();
        double tVelocity = components.topShooterMotor.getSelectedSensorVelocity();
        components.bottomShooterMotor.set(ControlMode.Velocity, setSpeedB);
        components.topShooterMotor.set(ControlMode.Velocity, -setSpeedT);
        
        if (loop % 50 == 0) {
            System.out.println((int)bVelocity + "  :  " + (int)tVelocity);
            System.out.println("     Set: " + (int)setSpeedB + "  :  " + (int)setSpeedT);
        }

        //addValue("B CP100ms", bVelocity);
        //addValue("T CP100ms", tVelocity);
        //addValue("S B CP100ms", setSpeedB);
        //addValue("S T CP100ms", setSpeedT);

        /*
        if (loop % 50 == 0) {
            System.out.println("B set speed error" + (bVelocity - setSpeedB));
            System.out.println("T set speed" + setSpeedT);
            System.out.println("T set speed error" +  (tVelocity - -setSpeedT));
        }
        */
    }

    private double getSetSpeedTop(double slider) {
        return GeneralUtils.lerp(0, 24000, slider);
    }

    private double getSetSpeedBottom(double slider) {
        return GeneralUtils.lerp(0, 10000, slider);
    }

    private void addValue(String category, double value) {
        CSVUtils.add("n.csv", timer.get() + "," + category + "," + value + ",0");
    }

    private void addNote(double note) {
        CSVUtils.add("n.csv", "0,Notes,0," + note);
    }

}
