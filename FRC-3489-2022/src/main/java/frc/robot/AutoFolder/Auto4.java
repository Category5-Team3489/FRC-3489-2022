package frc.robot.AutoFolder;

import frc.robot.ComponentsContainer;
import frc.robot.Constants;
import frc.robot.ShooterHandler;
public class Auto4 {
    ComponentsContainer container;
    private int currentStep = 1; 
    
    //name Handlers 
    private ShooterHandler ShooterHandler; 
 
    public void autonomous4init(){ 
        //reset the motor the method 
        resetEncoderPosition();
     } 
  //the actual code 
        private void resetEncoderPosition() {
        container.shooterTop.setSelectedSensorPosition(0);
     }
 //Encoder Position for Shooter, cargo transfer, and Intake 
    private double EncoderPositionForShooter(){
        return Math.abs(container.shooterTop.getSelectedSensorPosition());
    }

//Shoot into high
    private void Highshoot(){
        if (EncoderPositionForShooter() < Constants.auto4ShootHighClicks) {
            ShooterHandler.shoot(Constants.ShooterHighSpeed, Constants.ShooterHighSpeed);
      }
     else{
            currentStep ++; 
         ShooterHandler.stopShooter();
            resetEncoderPosition(); 
        }
    }   
// autonomous sequence 
    public void auto4(){
            switch (currentStep){
                case 1: 
                    Highshoot();
                    break;
                case 2: 
                    Auto1.auto1();
                    break; 
            }
        }
    


}