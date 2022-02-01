package frc.robot;

public class ShooterHandler {
    ComponentsContainer container;
    public ShooterHandler(){

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
