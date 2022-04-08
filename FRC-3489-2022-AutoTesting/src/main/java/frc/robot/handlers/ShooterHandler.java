package frc.robot.handlers;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.Constants;
import frc.robot.framework.RobotHandler;
import frc.robot.interfaces.IShuffleboardState;
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
        setShooter(getShooterSettingAtDistance(distance));
    }

    public void stop() {
        if (update(ShooterState.Disabled)) {
            setShooter(0, 0);
            setShuffleboardState();
        }
    }

    public boolean canShoot() {
        return currentBottomSpeed > Constants.Shooter.CanShootSpeedThreshold && currentTopSpeed > Constants.Shooter.CanShootSpeedThreshold;
    }

    public void setShooter(ShooterSetting setting) {
        setShooter(setting.bottomSpeed, setting.topSpeed);
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

    private int loop = 0;

    @Override
    public void teleopPeriodic() {
        double setSpeedB = getSetSpeedL((components.leftDriveJoystick.getThrottle() + 1d) / 2);
        double setSpeedT = getSetSpeed((components.rightDriveJoystick.getThrottle() + 1d) / 2);

        double bVelocity = components.bottomShooterMotor.getSelectedSensorVelocity();
        double tVelocity = components.topShooterMotor.getSelectedSensorVelocity();
        //addValue("B CP100ms", bVelocity);
        //addValue("T CP100ms", tVelocity);

        //addValue("S B CP100ms", setSpeedB);
        //addValue("S T CP100ms", setSpeedT);
        components.bottomShooterMotor.set(ControlMode.Velocity, setSpeedB);
        components.topShooterMotor.set(ControlMode.Velocity, -setSpeedT);
        
        if (loop % 50 == 0) {
            System.out.println((int)bVelocity + "  :  " + (int)tVelocity);
            System.out.println("     " + (int)setSpeedB + "  :  " + (int)setSpeedT);
            //System.out.println("B set speed error" + (bVelocity - setSpeedB));
            //System.out.println("T set speed" + setSpeedT);
            //System.out.println("T set speed error" +  (tVelocity - -setSpeedT));
        }
        loop++;
    }

    private double getSetSpeed(double slider) {
        return GeneralUtils.lerp(0, 24000, slider);
    }

    private double getSetSpeedL(double slider) {
        return GeneralUtils.lerp(0, 10000, slider);
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

    private ShooterSetting getShooterSettingAtDistance(double distance) {
        // find 2 indexes in table around point
        double dist = Constants.Shooter.ShooterSpeedTableStart;
        for (int i = 0; i < 7; i++) {
            double next = dist + Constants.Shooter.ShooterSpeedTableIncrement;
            if (distance <= next && distance >= dist) {
                ShooterSetting a = Constants.Shooter.ShooterSpeedTable[i];
                ShooterSetting b = Constants.Shooter.ShooterSpeedTable[i + 1];
                double t = (next - distance) / Constants.Shooter.ShooterSpeedTableIncrement;
                ShooterSetting c = new ShooterSetting(GeneralUtils.lerp(a.bottomSpeed, b.bottomSpeed, t), GeneralUtils.lerp(a.topSpeed, b.topSpeed, t));
                return c;
            }
            dist = next;
        }
        return new ShooterSetting(0, 0);
    }

    private void addValue(String category, double value) {
        CSVUtils.add("n.csv", timer.get() + "," + category + "," + value + ",0");
    }

    private void addNote(double note) {
        CSVUtils.add("n.csv", "0,Notes,0," + note);
    }

}
