package frc.robot;

public class ShooterHandler {
    ComponentsContainer container;
    public ShooterHandler(){

    }

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

    public void ShootHigh(){
        container.shooterLeft.set(Constants.ShooterHighSpeed);
        container.shooterRight.set(-Constants.ShooterHighSpeed);
    }
    public void ShootLow(){
        container.shooterLeft.set(Constants.ShooterLowSpeed);
        container.shooterRight.set(-Constants.ShooterLowSpeed);
    }
    public void stopShooting(){
        container.shooterLeft.stopMotor();
        container.shooterRight.stopMotor();
    }
    public void AdjustShootSpeed(double value){
        double CurrentSpeed = container.shooterLeft.get();
        if (CurrentSpeed + value <= 1){
            container.shooterLeft.set(CurrentSpeed + value);
            container.shooterRight.set(CurrentSpeed + value); 
            
        }      
        if (CurrentSpeed - value >= -1)  {
            container.shooterLeft.set(CurrentSpeed - value);
            container.shooterRight.set(CurrentSpeed - value);    
        }
    }
}
