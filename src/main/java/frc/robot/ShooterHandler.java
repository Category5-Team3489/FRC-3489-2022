package frc.robot;

import frc.robot.framework.RobotHandler;

public class ShooterHandler extends RobotHandler {
    
 
    //Shoot the ball
    public void shoot(double speedBottom, double speedTop) {
        components.shooterTop.set(speedTop);
        components.shooterBottom.set(speedBottom);
    }

    //Stop the motors
    public void stopShooter(){
        components.shooterBottom.stopMotor();
        components.shooterTop.stopMotor();
    }

    //Adjust speed
    public void adjustShooterSpeed(double increment){
        double currentSpeedTop = components.shooterTop.get();
        double currentSpeedBottom = components.shooterBottom.get();

        //Adjust top shooter
        if ((currentSpeedTop + increment > -1) && (currentSpeedTop + increment < 1)){
            components.shooterTop.set(currentSpeedTop + increment);
        }

        //Adjust bottom shooter
        if ((currentSpeedBottom + increment > -1) && (currentSpeedBottom + increment < 1)){
            components.shooterBottom.set(currentSpeedBottom + increment);
        }
    }

}
