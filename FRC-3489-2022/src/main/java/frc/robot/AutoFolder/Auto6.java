public class Auto6 {
    


    public void init(){
        resetEncoderPosition();
    }
    
    private int currentStep = 1;

    private DifferentialDrive differentialDrive;
    private ShooterHandler shooterHandler;
    private DriveHandler driveHandler;
    private IntakeHadler intakeHandler;
    private CargoTransferHandler cargoTransferHandler;
    private ComponentsContainer components;
    private Constants constants;
    
    //Shooter Encoder Clicks
    private double getEncoderPositionShooter(){
        return Math.abs(components.shooterTop.getSelectedSensorPosition());
    }
    //Drive Encoder Clicks
    private double getEncoderPositionDrive(){
        return Math.abs(components.frontLeftDrive.getSelectedSensorPosition());
    }
    //Cargo Transfer Encoder Clicks
    private double getEncoderPositionCargo(){
        return Math.abs(components.cargoMotorMover.getSelectedSensorPosition());
    }


    //Shoot
    private void shoot(){
        if (getEncoderPositionShooter()< constants.shootEncoderClicks){shooterHandler.ShootHigh();}
        else{
            currentStep++;
            shooterHandler.stop();
            resetEncoderPosition();}
        
        }
    //drive Forward and intake
    private void driveForwardIntake(){
         if (getEncoderPositionDrive()< constants.forwardEncoderClicks){driveHandler.tankDrive(0.5, 0.5);
        intakeHandler.intake();}
        else{
            currentStep++;
            driveHandler.stop();
            intakeHandler.stop();
            resetEncoderPosition();}
            }

    // Cargo Transfer
    private void cargoTransfer(){
        if (getEncoderPositionCargo()< constants.cargoTransferClicks){cargoTransferHandler.transferUp();}
        else{
            currentStep++;
            cargoTransferHandler.transferStop();
            resetEncoderPosition();
        }
    }

    //Turn Right
    private void turnRight(){
        if (getEncoderPositionDrive()< constants.turnRightEncoderClicks){driveHandler.tankDrive(0.7, 0.5);}
        else{
            currentStep++;
            driveHandler.stop();
            resetEncoderPosition();
        }
    }

    public void auto6(){
        switch(currentStep){
            case 1:
            shoot();
            break;
            case 2:
            driveForwardIntake();
            break;
            case 3:
            cargoTransfer();
            break;
            case 4:
            turnRight();
            break;
            case 5:
            driveForward();
            break;
            


            
        }

    }


    
    

        

}

