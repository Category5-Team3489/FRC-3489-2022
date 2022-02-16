package frc.robot.handlers;

import frc.robot.Constants;
import frc.robot.framework.RobotHandler;

public class ShooterHandler extends RobotHandler {
    
    public void shootHigh() {
        setShooter(Constants.ShootHighBottomMotorSpeed, Constants.ShootHighTopMotorSpeed);
    }

    public void shootLow() {
        setShooter(Constants.ShootLowBottomMotorSpeed, Constants.ShootLowTopMotorSpeed);
    }

    public void stopShooter() {
        setShooter(0, 0);
    }


    public void setShooter(double bottomSpeed, double topSpeed) {
        components.bottomShooterMotor.set(bottomSpeed);
        components.topShooterMotor.set(topSpeed);
    }

}
