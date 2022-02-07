package frc.robot.AutoFolder;


import frc.robot.ComponentsContainer;
import frc.robot.Constants;
import frc.robot.DriveHandler;

public class Auto5 {
    public void Autonomous5Init() {
        resetEncoderPosition();
    }

    //classes Needed
    private DriveHandler driveHandler;
    private ComponentsContainer container;
    private Auto3 auto3;

    private int currentStep = 1;

 
    public static final double auto5LeftMotorForTurn = 0.2;
    public static final double auto5RightMotorForTurn = 0.4;
        
    public static final double auto5DriveForwardClicks = 4000;
    public static final double auto5TurnLeftClicks = 4000;

    private void resetEncoderPosition() {
        container.frontLeftDrive.setSelectedSensorPosition(0);
    }

    private double getEncoderPositionAbs() {
        return Math.abs(container.frontLeftDrive.getSelectedSensorPosition());
    }

    private void driveForward(){
        if (getEncoderPositionAbs() < Constants.auto5DriveForwardClicks){
            driveHandler.tankDrive(Constants.driveForwardSpeed, Constants.driveForwardSpeed);
        } 
        else {
            resetEncoderPosition();
            driveHandler.stop();
            currentStep ++;
        }
    }

    private void turnLeft(){
        if (getEncoderPositionAbs() < Constants.auto5TurnLeftClicks){
            driveHandler.tankDrive(Constants.auto5LeftMotorForTurn, Constants.auto5RightMotorForTurn);
        } 
        else {
            resetEncoderPosition();
            driveHandler.stop();
            currentStep ++;
        }
    }
    private void auto3Code(){
        auto3.auto3();
        currentStep++;
    }

    public void autonomous5periodic(){
        switch(currentStep){
            case 1:
                auto3Code();
                break;
            case 2:
                turnLeft();
                break;
            case 3:
                driveForward();
                break;
        }
    }







}