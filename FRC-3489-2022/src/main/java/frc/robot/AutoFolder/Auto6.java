package frc.robot.AutoFolder;


import frc.robot.ComponentsContainer;
import frc.robot.DriveHandler;
import frc.robot.IntakeHandler;
import frc.robot.ShooterHandler;
import frc.robot.CargoTransferHandler;

public class Auto6 {
    
    ComponentsContainer components;
    private static final double shootEncoderClicks = 4000;
    private static final double forwardEncoderClicks = 4000;
    private static final double intakeEncoderClicks =4000;
    private static final double cargoTransferClicks = 4000;
    private static final double turnRightEncoderClicks = 4000;
    private int currentStep = 1;

    public void init(){
        resetEncoderPosition();
    }

    private ShooterHandler shooterHandler;
    private DriveHandler driveHandler;
    private IntakeHandler intakeHandler;
    private CargoTransferHandler cargoTransferHandler;

    private void shoot(){
        if (getEncoderPositionAbs()< shootEncoderClicks){shooterHandler.ShootHigh();}
        else{
            currentStep++;
            shooterHandler.stop();
            resetEncoderPosition();}
        }

    private void driveForward(){
         if (getEncoderPositionAbs()< forwardEncoderClicks){driveHandler.tankDrive(0.5, 0.5);}
        else{
            currentStep++;
            driveHandler.stop();
            resetEncoderPosition();}
            }

    private void intakeStart(){
        if (getEncoderPositionAbs()< intakeEncoderClicks){intakeHandler.intake();}
        else{
            currentStep++;
            inkakeHandler.stop();
            resetEncoderPosition();}
        }

    

    private void cargoTransfer(){
        if (getEncoderPositionAbs()< cargoTransferClicks){cargoTransferHandler.transferUp();}
        else{
            currentStep++;
            cargoTransferHandler.transferStop();
            resetEncoderPosition();
        }
    }
    private void turnRight(){
        if (getEncoderPositionAbs()< turnRightEncoderClicks){driveHandler.tankDrive(0.5, 0.3);}
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
            driveForward();
            break;
            case 3:
            intakeStart();
            break;
            case 4:
            cargoTransfer();
            break;
            case 5:
            turnRight();
            break;
            case 6:
            driveForward();
            break;
            


            
        }

    }


    
    

        

}

