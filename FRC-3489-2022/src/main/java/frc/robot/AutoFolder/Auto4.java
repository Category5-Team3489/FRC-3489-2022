package frc.robot.AutoFolder;

import frc.robot.ComponentsContainer;
import frc.robot.Constants;

import frc.robot.ShooterHandler;
import frc.robot.CargoTransferHandler;

public class Auto4 {
    ComponentsContainer container;
    private int currentStep = 1; 
    
    //name Handlers 

    private ShooterHandler ShooterHandler; 
    private CargoTransferHandler cargo;
    private Auto1 auto1;
 
    public void autonomous4init(){ 
        //reset the motor the method 
        resetEncoderPosition();
     } 
  //the actual code 
        private void resetEncoderPosition() {
        container.intakeMotor.setSelectedSensorPosition(0);
        container.shooterTop.setSelectedSensorPosition(0);
     }
 //Encoder Position for Shooter, cargo transfer, and Intake 
    private double EncoderPositionForShooter(){
        return Math.abs(container.shooterTop.getSelectedSensorPosition());
    }
  
    private double getEncoderPositionForCargo(){
        return Math.abs(container.cargoMoverMotor.getSelectedSensorPosition());
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

// Cargo Mover 
    private void cargoTransfer(){
        if(getEncoderPositionForCargo() < Constants.auto4CargoMoverClicks)
            cargo.transferUp();
        else{
            currentStep ++;
            resetEncoderPosition();
            cargo.transferStop();
        }

    }
    private void auto1Code(){
        auto1.auto1();
        currentStep ++;
    }
// autonomous sequence 
    public void auto4(){
            switch (currentStep){
                case 1:
                    cargoTransfer();
                    break;
                case 3: 
                    Highshoot();
                    break;
                case 4: 
                    auto1Code();
                    break; 
            }
        }
    


}