package frc.robot.handlers;

import frc.robot.Constants;
import frc.robot.framework.RobotHandler;

public class ShooterHandler extends RobotHandler {

    private double lastBottomSpeed = 0;
    private double lastTopSpeed = 0;

    /*
    private final static double kP = 0.11;
    private final static double kI = 0.0001;//0.001;//0.0005;//0.001;
    private final static double kD = 25;//2;//5;
    private final static double kF = 0;
    private final static double Iz = 750; // required error to reset I accumulator
    */

    public void shootLow() {
        setShooter(Constants.ShootLowBottomMotorSpeed, Constants.ShootLowTopMotorSpeed);
        shuffleboardHandler.setString(true, "Shooter Mode", "Low");
    }
    
    public void shootHigh() {
        setShooter(Constants.ShootHighBottomMotorSpeed, Constants.ShootHighTopMotorSpeed);
        shuffleboardHandler.setString(true, "Shooter Mode", "High");
    }

    public void setWrongColor() {
        setShooter(Constants.WrongColorBottomSpeed, Constants.WrongColorTopSpeed);
        shuffleboardHandler.setString(true, "Shooter Mode", "Wrong Color");
    }

    public void stopShooter() {
        setShooter(0, 0);
        shuffleboardHandler.setString(true, "Shooter Mode", "Stopped");
    }

    public boolean canShoot() {
        return Math.abs(lastBottomSpeed) > 0.1 && Math.abs(lastTopSpeed) > 0.1;
    }

    public void setShooter(double bottomSpeed, double topSpeed) {
        lastBottomSpeed = bottomSpeed;
        lastTopSpeed = topSpeed;
        components.bottomShooterMotor.set(bottomSpeed);
        components.topShooterMotor.set(-topSpeed);
    }

}
