package frc.robot.AutoFolder;
// This code needs the Drive foward to intake the ball and then to drive foward to the terminal to get more balls in 


import frc.robot.IntakeHandler;
import frc.robot.ComponentsContainer;
import frc.robot.Constants;
import frc.robot.ShooterHandler;
import frc.robot.DriveHandler;
import frc.robot.CargoTransferHandler;

public class Auto7 {
    ComponentsContainer container;
    private int currentStep = 1;

    //name Handlers 
    private IntakeHandler IntakeHandler; 
    private ShooterHandler ShooterHandler; 
    private DriveHandler DriveHandler; 
    private CargoTransferHandler CargoHandler; 
    
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
        resetEncoderPosition();
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
    private double getEncoderPositionForCargoMover(){
        return Math.abs(container.cargoMoverMotor.getSelectedSensorPosition());
    }
    private double getEncoderPositionAbs() {
        return Math.abs(container.frontLeftDrive.getSelectedSensorPosition());
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
        if(EncoderPositionForIntake() < intakeClicks) {
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
        if(getEncoderPositionForCargoMover() < transferClicks)
            CargoHandler.transferUp();
        else{
            currentStep ++;
            resetEncoderPosition();
            CargoHandler.transferStop();
        }

    }
    //drive foward  
    private void driveForward() {
        if (getEncoderPositionAbs() < DriveForwardClicks) {
            DriveHandler.tankDrive(DriveForwardSpeed, DriveForwardSpeed);
        }
        else {
            currentStep++;
            resetEncoderPosition();
            DriveHandler.stop();
        }
    }
    //drive Turn tankdrive(is empty need right and left);
    private void driveTurn() {
        if (getEncoderPositionAbs() < Driveturnclicks) {
            DriveHandler.tankDrive(leftMotorForTurn, rightMotorForTurn); 

        }
        else {
            currentStep++;
            resetEncoderPosition();
            DriveHandler.stop();
        }
    }
    //autonomous sequence 
    public void auto7(){
        switch (currentStep){
            case 1: 
                Highshoot();
                break;
            case 2:
                driveTurn();
                break;
            case 3: 
                driveForward();
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
                driveForward();
                break; 




        }

    }
    
}
