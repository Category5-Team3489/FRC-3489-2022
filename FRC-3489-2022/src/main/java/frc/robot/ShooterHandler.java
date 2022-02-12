package frc.robot;

import frc.robot.framework.RobotHandler;

public class ShooterHandler extends RobotHandler {
    ComponentsContainer container;
    public ShooterHandler(){

    }

    //Shoot the ball
    public void shoot(double speedBottom, double speedTop) {
        container.shooterTop.set(speedTop);
        container.shooterBottom.set(speedBottom);
    }

    //Stop the motors
    public void stopShooter(){
        container.shooterBottom.stopMotor();
        container.shooterTop.stopMotor();
    }

    //Adjust speed
    public void adjustShooterSpeed(double increment){
        double currentSpeedTop = container.shooterTop.get();
        double currentSpeedBottom = container.shooterBottom.get();

        //Adjust top shooter
        if ((currentSpeedTop + increment > -1) && (currentSpeedTop + increment < 1)){
            container.shooterTop.set(currentSpeedTop + increment);
        }

        //Adjust bottom shooter
        if ((currentSpeedBottom + increment > -1) && (currentSpeedBottom + increment < 1)){
            container.shooterBottom.set(currentSpeedBottom + increment);
        }
    }

    public void ShootHigh(){
        container.shooterTop.set(Constants.ShooterHighSpeed);
        container.shooterBottom.set(-Constants.ShooterHighSpeed);
    }
    public void ShootLow(){
        container.shooterTop.set(Constants.ShooterLowSpeed);
        container.shooterBottom.set(-Constants.ShooterLowSpeed);
    }
    public void AdjustShootSpeed(double value){
        double CurrentSpeed = container.shooterTop.get();
        if (CurrentSpeed + value <= 1){
            container.shooterTop.set(CurrentSpeed + value);
            container.shooterBottom.set(CurrentSpeed + value); 
            
        }      
        if (CurrentSpeed - value >= -1)  {
            container.shooterTop.set(CurrentSpeed - value);
            container.shooterBottom.set(CurrentSpeed - value);    
        }
    }
}
