package frc.robot;
// This code needs the Drive foward to intake the ball and then to drive foward to the terminal to get more balls in 

import javax.swing.text.AbstractDocument.BranchElement;

public class Auto7 {
    ComponentsContainer container;
    private int currentStep = 1;

    //name Handlers 
    private IntakeHandler IntakeHandler; 
    private ShooterHandler ShooterHandler; 
    private DriveHandler DriveHandler; 
    private CargoHandler CargoHandler; 
    
    //drive Speeds Program Turn speeds Left and Right 
    private static final double DriveForwardSpeed = 0.65;
    private static final double leftMotorForTurn = 0.2;
    private static final double rightMotorForTurn = 0.4;
         
    //name the Encoders 
    private static final double ShootHighClicks = 4000;
    private static final double intakeClicks = 4000; 
    public static final double DriveForwardClicks = 4000;
    public static final double transferClicks = 4000; 
    public static final double Driveturnclicks = 4000; 
    
    public void autonomous7init(){ 
        //reset the motor the method 
        resetEncoderPostition();
    }
     
    private void resetEncoderPosition() {
        container.intakeMotor.setSelectedSensorPosition(0);    
        container.shooterTop.setSelectedSensorPosition(0);
        container.frontLeftDrive.setSelectedSensorPosition(0);
     }
    //Encoder Position for Shooter and Intake, drive and Cargo 
    private double EncoderPositionForShooter(){
        return Math.abs(container.shooterTop.getSelectedSensorPosition());
    }
    private double EncoderPositionForIntake(){
        return Math.abs(container.intakeMotor.getSelectedSensorPosition());
    }
    private double getEncoderPositionIntake() {
        return Math.abs(container.intakeMotor.getSelectedSensorPosition());
    }
    private double getEncoderPositionAbs() {
        return Math.abs(container.frontleftDrive.getSelectedSensorPosition());
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
    //Intake TIME 
    private void intakeIntake(){
        if(EncoderPositionForShooter() < intakeClicks) {
            IntakeHandler.intake();
        }
        else{
            currentStep ++; 
            ShooterHandler.stopShooter();
            resetEncoderPosition();
        }

    }
    //CargoMover Time 
    private void cargoTransfer(){
        if(getEncoderPositionIntake() < transferClicks)
            intakeHandler.intake();
        else{
            currentStep ++;
            resetEncoderPosition();
            intakeHandler.stopIntake();
        }

    }
    //drive foward  
    private void driveForward() {
        if (getEncoderPositionAbs() < DriveForwardClicks) {
            driveHandler.tankDrive(DriveForwardSpeed, DriveForwardSpeed);
        }
        else {
            currentStep++;
            resetEncoderPosition();
            driveHandler.stop();
        }
    }
    //drive Turn tankdrive(is empty need right and left);
    private void driveTurn() {
        if (getEncoderPositionabs() < Driveturnclicks) {
            driveHandler.tankDrive(leftMotorForTurn, rightMotorForTurn); 

        }
        else {
            currentStep++;
            resetEncoderPosition();
            driveHandler.stop();
        }
    }
    //autonomous sequence 
    public void auto7(){
        switch (currentstep){
            case 1: 
                Highshoot();
                break;
            case 2:
                driveTurn();
                break;
            case 3: 
                driveFoward();
                break;
            case 4: 
                cargoTransfer();
                break;
            case 5: 
                intakeIntake(); 
                break; 
            case 6: 
                driveTurn();
                break;
            case 7: 
                driveFoward();
                break; 




        }

    }
    
}
