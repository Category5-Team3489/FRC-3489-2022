package frc.robot.AutoFolder;

import frc.robot.CargoTransferHandler;
import frc.robot.ComponentsContainer;
import frc.robot.Constants;
import frc.robot.DriveHandler;
import frc.robot.IntakeHandler;
import frc.robot.ShooterHandler;

//Shoot, Intake, Turn Right, Drive forward
public class Auto6 {
    

    public void init(){
        resetEncoderPosition();
    }
    
    private int currentStep = 1;

    //Handlers

    private ShooterHandler shooterHandler;
    private DriveHandler driveHandler;
    private IntakeHandler intakeHandler;
    private CargoTransferHandler cargoTransferHandler;
    private ComponentsContainer components;
    
    private void resetEncoderPosition() {
        components.cargoMoverMotor.setSelectedSensorPosition(0);
        components.frontLeftDrive.setSelectedSensorPosition(0);
        components.shooterTop.setSelectedSensorPosition(0);
    }
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
        return Math.abs(components.cargoMoverMotor.getSelectedSensorPosition());
    }


    //Shoot
    private void shoot(){
        if (getEncoderPositionShooter()< Constants.auto6ShootEncoderClicks){shooterHandler.ShootHigh();}
        else{
            currentStep++;
            shooterHandler.stopShooter();
            resetEncoderPosition();}
        
        }
    //drive Forward and intake
    private void driveForwardIntake(){
         if (getEncoderPositionDrive()< Constants.auto6ForwardEncoderClicks){driveHandler.tankDrive(0.5, 0.5);
            intakeHandler.intake();}
        else{
            currentStep++;
            driveHandler.stop();
            intakeHandler.stop();
            resetEncoderPosition();}
            }

    // Cargo Transfer
    private void cargoTransfer(){
        if (getEncoderPositionCargo()< Constants.auto6CargoTransferClicks){cargoTransferHandler.transferUp();}
        else{
            currentStep++;
            cargoTransferHandler.transferStop();
            resetEncoderPosition();
        }
    }

    //Turn Right
    private void turnRight(){
        if (getEncoderPositionDrive()< Constants.auto6TurnRightEncoderClicks){driveHandler.tankDrive(0.7, 0.5);}
        else{
            currentStep++;
            driveHandler.stop();
            resetEncoderPosition();
        }
    }

   
    //sequence
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
            driveForwardIntake();
            break;
            
        }

    }


    
    

        

}

