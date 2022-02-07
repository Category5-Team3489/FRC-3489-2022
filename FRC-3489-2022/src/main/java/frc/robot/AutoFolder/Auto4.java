package frc.robot.AutoFolder;

import frc.robot.ComponentsContainer;
import frc.robot.Constants;
import frc.robot.IntakeHandler;
import frc.robot.ShooterHandler;
import frc.robot.CargoTransferHandler;

public class Auto4 {
    ComponentsContainer container;
    private int currentStep = 1; 
    
    //name Handlers 
    private IntakeHandler IntakeHandler;
    private ShooterHandler ShooterHandler; 
    private CargoTransferHandler cargo;

    //Clicks for Encoder 
    public static final double ShootHighClicks = 4000;
    public static final double auto4CargoMoverClicks = 4000; 
 
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
    private double EncoderPositionForIntake(){
        return Math.abs(container.intakeMotor.getSelectedSensorPosition());
    }
    private double getEncoderPositionForCargo(){
        return Math.abs(container.cargoMoverMotor.getSelectedSensorPosition());
    }

//Shoot into high
    private void Highshoot(){
        if (EncoderPositionForShooter() < ShootHighClicks) {
            ShooterHandler.shoot(Constants.ShooterHighSpeed, Constants.ShooterHighSpeed);
      }
     else{
            currentStep ++; 
         ShooterHandler.stopShooter();
            resetEncoderPosition(); 
        }
    }   
//intake  
    private void intakeCargo(){
     if (EncoderPositionForIntake() < intakeClicks) {
            IntakeHandler.intake();
     }
     else{
            currentStep ++; 
        IntakeHandler.stop();
            resetEncoderPosition();
     } 
    }
// Cargo Mover 
    private void cargoTransfer(){
        if(getEncoderPositionForCargo() < cargoMoverClicks)
            cargo.transferUp();
        else{
            currentStep ++;
            resetEncoderPosition();
            cargo.transferStop();
        }

        }
// autonomous sequence 
    public void auto4(){
            switch (currentStep){
                case 1:
                    cargoTransfer();
                    break;
                case 2:
                    intakeCargo();
                    break;
                case 3: 
                    Highshoot();
                    break;
                case 4: 
                    Auto1.auto1();
                    break; 
            }
        }
    


}