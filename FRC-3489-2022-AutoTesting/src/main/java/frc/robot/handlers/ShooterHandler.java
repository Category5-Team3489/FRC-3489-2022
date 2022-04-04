package frc.robot.handlers;

import frc.robot.Constants;
import frc.robot.Constants.Shooter;
import frc.robot.framework.RobotHandler;
import frc.robot.interfaces.IShuffleboardState;
import frc.robot.types.ShooterSetting;
import frc.robot.types.ShooterState;
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

    public void stop() {
        if (update(ShooterState.Disabled)) {
            setShooter(0, 0);
            setShuffleboardState();
        }
    }

    public boolean canShoot() {
        return currentBottomSpeed > Constants.Shooter.CanShootSpeedThreshold && currentTopSpeed > Constants.Shooter.CanShootSpeedThreshold;
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

}
