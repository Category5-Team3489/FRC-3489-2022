package frc.robot;


public class Auto4 {
    ComponentsContainer container;
    private int currentStep = 1; 
    
    //name Handlers 
    private IntakeHandler IntakeHandler;
    private ShooterHandler ShooterHandler; 
    private CargoTransferHandler cargo;

    //Clicks for Encoder 
    private static final double ShootHighClicks = 4000;
    private static final double intakeClicks = 4000; 
    private static final double cargoMoverClicks = 4000; 
 
    public void autonomous4init(){ 
        //reset the motor the method 
        resetEncoderPostition();
     } 
  //the actual code 
        private void resetEncoderPosition() {
        container.intakeMotor.setSelectedSensorPosition(0);
        container.shooterTop.setSelectedSensorPosition(0);
     }
 //Encoder Position for Shooter and Intake 
    private double EncoderPositionForShooter(){
        return Math.abs(container.shooterTop.getSelectedSensorPosition());
    }
    private doube EncoderPositionForIntake(){
        return Math.abs(container.intakeMotor.getSelectedSensorPosition());
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
        IntakeHandler.Stopintake();
            resetEncoderPosition();
     } 
    }
// Cargo Mover 
    private void cargoTransfer(){
        if(getEncoderPositionIntake() < cargoMoverClicks)
            cargo.transferUp();
        else{
            currentStep ++;
            resetEncoderPosition();
            cargo.transferStop();
        }

        }
// autonomous sequence 
    public void auto4(){
            switch (currentstep){
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