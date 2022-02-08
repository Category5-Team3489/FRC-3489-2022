package frc.robot;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class Auto1 {
    private ComponentsContainer components;
    private DifferentialDrive differentialDrive;
    private DriveHandler driveHandler;
    private Constants constants;
    private int currentStep = 1;
    
   
    public void init(){
        resetEncoderPosition();
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
        if (getEncoderPositionAbs()< constants.forwardEncoderClicks){driveHandler.tankDrive(0.5, 0.5);}
        else{
            currentStep++;
            driveHandler.stop();
            resetEncoderPosition();
        }

    }
        private void resetEncoderPosition(){
            components.frontLeftDrive.setSelectedSensorPosition(0);
        }

    
        //Turn Left
    private void turnLeft(){
        if (getEncoderPositionAbs()<constants.turnLeft){
            driveHandler.tankDrive(0.4, 0.7);
        }
        else{
            currentStep++;
            driveHandler.stop();
            resetEncoderPosition();

        }
      
    }
   
    
    private double getEncoderPositionAbs(){
        return Math.abs(components.frontRightDrive.getSelectedSensorPosition());
    }

   
    
}
