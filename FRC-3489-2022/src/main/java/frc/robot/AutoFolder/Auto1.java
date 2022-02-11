package frc.robot.AutoFolder;

import frc.robot.ComponentsContainer;
import frc.robot.Constants;
import frc.robot.DriveHandler;

//Move Forward, Turn Left, Move Forward

public class Auto1 {
    private ComponentsContainer components;

    private DriveHandler driveHandler;
    private Constants constants;
    private int currentStep = 1;

    private double getEncoderPositionAbs(){
        return Math.abs(components.frontRightDrive.getSelectedSensorPosition());
    }
   
    public void init(){
        resetEncoderPosition();
    }

    private void resetEncoderPosition(){
        components.frontLeftDrive.setSelectedSensorPosition(0);
    }

    //sequence
    public void auto1(){
        switch(currentStep){
            case 1:
            driveForward();
            break;
            case 2:
            turnLeft();
            break;
            case 3:
            driveForward();
            break;
        }

    }
    //Drive Forward
    private void driveForward(){
        if (getEncoderPositionAbs()< constants.auto1ForwardEncoderClicks){
            driveHandler.tankDrive(0.5, 0.5);}
        else{
            currentStep++;
            driveHandler.stop();
            resetEncoderPosition();
        }

    }
        //Turn Left
    private void turnLeft(){
        if (getEncoderPositionAbs()<constants.auto1TurnLeft){
            driveHandler.tankDrive(0.4, 0.7);
        }
        else{
            currentStep++;
            driveHandler.stop();
            resetEncoderPosition();

        }
      
    }
   
}

