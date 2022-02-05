package frc.robot;

//import com.ctre.phoenix.CANifier.GeneralPin;

public class Auto2 {
    ComponentsContainer container;
    private int currentStep = 1; 

    //handlers needed
    private DriveHandler driveHandler;
    private ShooterHandler shootHandler;
    private IntakeHandler intakeHandler;
    
    //drive speeds
    private static final double DriveForwardSpeed = 0.65;
    private static final double driveBackwardSpeed = 0.65;

    //encoder click amounts
    private static final double DriveForwardClicks = 1;
    private static final double ShootHighClicks = 1;
    private static final double driveBackwardClicks = 1;
    private static final double intakeClicks = 1;
    

    public void autonomous2init(){
        //set motor positions to zero
        resetEncoderPosition();
    }
    //reset encoder clicks
    private void resetEncoderPosition() {
        container.frontLeftDrive.setSelectedSensorPosition(0);
        container.intakeMotor.setSelectedSensorPosition(0);
        container.shooterTop.setSelectedSensorPosition(0);
    }
    //gets encoder position
    private double getEncoderPositionAbs() {
        return Math.abs(container.frontLeftDrive.getSelectedSensorPosition());
    }

    private double getEncoderPositionIntake() {
        return Math.abs(container.intakeMotor.getSelectedSensorPosition());
    }

    private double getEncoderPositionShooter() {
        return Math.abs(container.shooterTop.getSelectedSensorPosition());
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
    //drive back
    private void driveBackward(){
        if (getEncoderPositionAbs() < driveBackwardClicks) {
            driveHandler.tankDrive(driveBackwardSpeed, driveBackwardSpeed);
        }
        else{
            currentStep ++;
            resetEncoderPosition();
            driveHandler.stop();
        }
    }
    //intake
    private void intake(){
        if (getEncoderPositionIntake() < intakeClicks) {
            intakeHandler.intake();
        } 
        else {
            currentStep ++;
            intakeHandler.stopIntake();
            resetEncoderPosition();
        }
    }
    //shoot high
    private void shootHigh() {
        if(getEncoderPositionShooter() < ShootHighClicks){
            shootHandler.shoot(Constants.ShooterHighSpeed, Constants.ShooterHighSpeed);
        }
        else{
            currentStep ++;
            resetEncoderPosition();
            shootHandler.stopShooter();
        }
    }
    //the autonomous sequence
    public void auto2(){
       switch (currentStep){
        case 1:
            shootHigh();
            break;
        case 2:
            driveForward();
            break;
        case 3:
            intake();
            break;
       case 4:
            driveBackward();
            break;
        case 5:
            shootHigh();
            break;
       }
    }
}
