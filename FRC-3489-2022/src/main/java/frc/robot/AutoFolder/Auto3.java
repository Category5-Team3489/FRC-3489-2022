package frc.robot.AutoFolder;

import frc.robot.DriveHandler;
import frc.robot.IntakeHandler;
import frc.robot.ShooterHandler;
import frc.robot.CargoTransferHandler;
import frc.robot.ComponentsContainer;
import frc.robot.Constants;


public class Auto3 {
    //handlers needed
    private DriveHandler driveHandler;
    private ShooterHandler shootHandler;
    private IntakeHandler intakeHandler;
    private CargoTransferHandler cargo;
    private ComponentsContainer container;
    
    private int currentStep = 1;

  

    public void autonomous3init(){
        //set motor positions to zero
        resetEncoderPosition();
    }
    //reset encoder clicks
    private void resetEncoderPosition() {
        container.frontLeftDrive.setSelectedSensorPosition(0);
        container.shooterTop.setSelectedSensorPosition(0);
        container.cargoMoverMotor.setSelectedSensorPosition(0);
    }
    //gets encoder position
    private double getEncoderPositionAbs() {
        return Math.abs(container.frontLeftDrive.getSelectedSensorPosition());
    }
    private double getEncoderPositionShooter() {
        return Math.abs(container.shooterTop.getSelectedSensorPosition());
    }
    private double getEncoderPositionCargo() {
        return Math.abs(container.cargoMoverMotor.getSelectedSensorPosition());
    }

    //drive foward and intake
    private void driveForward() {
        if (getEncoderPositionAbs() < Constants.Auto3DriveForwardClicks) {
            driveHandler.tankDrive(Constants.driveForwardSpeed, Constants.driveForwardSpeed);
            intakeHandler.intake();
        }
        else {
            currentStep ++;
            resetEncoderPosition();
            driveHandler.stop();
            intakeHandler.stop();
        }
    }
    //drive back
    private void driveBackward(){
        if (getEncoderPositionAbs() < Constants.Auto3DriveBackwardClicks) {
            driveHandler.tankDrive(Constants.driveBackwardSpeed, Constants.driveBackwardSpeed);
        }
        else{
            currentStep ++;
            resetEncoderPosition();
            driveHandler.stop();
        }
    }

    //shoot high
    private void shootHigh() {
        if(getEncoderPositionShooter() < Constants.Auto3ShootHighClicks){
            cargoTransfer();
            shootHandler.shoot(Constants.ShooterHighSpeed, Constants.ShooterHighSpeed);
        }
        else{
            currentStep ++;
            resetEncoderPosition();
            shootHandler.stopShooter();
        }
    }
    //transfer the ball to the shooter
    private void cargoTransfer(){
        if(getEncoderPositionCargo() < Constants.Auto3TransferClicks)
           cargo.transferUp();
        else{
            currentStep ++;
            resetEncoderPosition();
            cargo.transferStop();
        }

    }

    //the autonomous sequence
    public void auto3(){
       switch (currentStep){
            case 1:
                driveForward();
                break;
            case 2:
                driveBackward();
                break;
            case 3:
                shootHigh();
                break;
            case 4:
                shootHigh();
                break; 
       }
    }
}