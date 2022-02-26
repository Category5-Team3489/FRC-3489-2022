package frc.robot.handlers;

import frc.robot.Constants;
import frc.robot.framework.RobotHandler;

public class ShooterHandler extends RobotHandler {

    private double lastBottomSpeed = 0;
    private double lastTopSpeed = 0;
    
    public void shootHigh() {
        setShooter(Constants.ShootHighBottomMotorSpeed, Constants.ShootHighTopMotorSpeed);
    }

    public void shootLow() {
        setShooter(Constants.ShootLowBottomMotorSpeed, Constants.ShootLowTopMotorSpeed);
    }

    public void stopShooter() {
        setShooter(0, 0);
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

    public void wrongColor() {
        setShooter(Constants.WrongColorBottomSpeed, Constants.WrongColorTopSpeed);
    }

}
