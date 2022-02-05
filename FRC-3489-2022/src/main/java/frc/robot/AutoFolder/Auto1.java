package frc.robot;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class Auto1 {
    ComponentsContainer components;
    private int currentStep = 1;
    private static final double encoderClicks = 4000;
    private static final double turnLeft = 2000;
   
    public void init(){
        resetEncoderPosition();
    }

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

    private DifferentialDrive differentialDrive;
    private DriveHandler driveHandler;
    

    private void driveForward(){
        if (getEncoderPositionAbs()< encoderClicks){driveHandler.tankDrive(0.5, 0.5);}
        else{
            currentStep++;
            driveHandler.stop();
            resetEncoderPosition();
        }

    }
        private void resetEncoderPosition(){
            components.frontLeftDrive.setSelectedSensorPosition(0);
        }

    

    private void turnLeft(){
        if (getEncoderPositionAbs()<turnLeft){
            driveHandler.tankDrive(0.2, 0.3);
        }
        else{
            currentStep++;
            driveHandler.stop();
            resetEncoderPosition();

        }
      
    }
   
    private double getEncoderPositionAbs(){
        return Math.abs(components.frontLeftDrive.getSelectedSensorPosition());
    }

   
    
}
