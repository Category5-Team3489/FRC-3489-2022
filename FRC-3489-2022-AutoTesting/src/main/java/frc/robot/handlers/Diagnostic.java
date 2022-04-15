package frc.robot.handlers;

import frc.robot.Constants;
import frc.robot.containers.ComponentsContainer;
import frc.robot.framework.RobotHandler;

public class Diagnostic extends RobotHandler {

    private ComponentsContainer components;

    public boolean intake() {
        components.intakeMotor.set(1);
        double intake = components.intakeMotor.getSelectedSensorVelocity();
        components.intakeMotor.stopMotor();
        if(intake == 10){
            return true;
        }
        return false;
    }

    public boolean cargoTransfer() {
        components.cargoTransferMotor.set(1);
        double cargoTransfer = components.cargoTransferMotor.getSelectedSensorVelocity();
        components.cargoTransferMotor.stopMotor();
        if(cargoTransfer == 10){
            return true;
        }
        return false;
    }

    public boolean topShooter() {
        components.topShooterMotor.set(1);
        double topShooter = components.topShooterMotor.getSelectedSensorVelocity();
        components.topShooterMotor.stopMotor();
        if(topShooter == 10){
            return true;
        }
        return false;
    }
    
    public boolean bottomShooter() {
        components.bottomShooterMotor.set(1);
        double bottomShooter = components.bottomShooterMotor.getSelectedSensorVelocity();
        components.bottomShooterMotor.stopMotor();
        if(bottomShooter == 10){
            return true;
        }
        return false;
    }

    public boolean leftDrive() {
        components.drive.tankDrive(1, 0);
        double leftFront = components.leftFrontDriveMotor.getOutputCurrent();
        double leftFollow = components.leftFollowerDriveMotor.getOutputCurrent();
        components.drive.stopMotor();
        if(leftFront == 10 && leftFollow == 10){
            return true;
        }
        return false;
    }

    public boolean leftDriveReverse() {
        components.drive.tankDrive(-1, 0);
        double leftFront = components.leftFrontDriveMotor.getOutputCurrent();
        double leftFollow = components.leftFollowerDriveMotor.getOutputCurrent();
        components.drive.stopMotor();
        if(leftFront == 10 && leftFollow == 10){
            return true;
        }
        return false;
    }

    public boolean rightDrive() {
        components.drive.tankDrive(0, 1);
        double rightFront = components.rightFrontDriveMotor.getOutputCurrent();
        double rightFollow = components.rightFollowerDriveMotor.getOutputCurrent();
        components.drive.stopMotor();
        if(rightFront == 10 && rightFollow == 10){
            return true;
        }
        return false;
    }

    public boolean rightDriveReverse() {
        components.drive.tankDrive(0, -1);
        double rightFront = components.rightFrontDriveMotor.getOutputCurrent();
        double rightFollow = components.rightFollowerDriveMotor.getOutputCurrent();
        components.drive.stopMotor();
        if(rightFront == 10 && rightFollow == 10){
            return true;
        }
        return false;
    }

    public boolean telescope() {
        components.telescopeMotor.set(1);
        double telescope = components.telescopeMotor.getSelectedSensorVelocity();
        components.telescopeMotor.stopMotor();
        if(telescope == 10){
            return true;
        }
        return false;
    }

    public boolean navx() {
        float navx = components.navx.getPitch();
        if(navx >= -180 && navx <= 180){
            return true;
        }
        return false;
    }

    public boolean intakeLaser() {
        boolean intakeLaser = components.intakeLaserSensor.get();
        return intakeLaser;
    }

    public void getBaseLine(){
        //Intake
        components.intakeMotor.set(1);
        double intake = components.intakeMotor.getSelectedSensorVelocity();
        System.out.println("intake: "+intake);
        components.intakeMotor.stopMotor();

        //Cargo Transfer
        components.cargoTransferMotor.set(1);
        double cargoTransfer = components.cargoTransferMotor.getSelectedSensorVelocity();
        System.out.println("Cargo Transfer: "+cargoTransfer);
        components.cargoTransferMotor.stopMotor();

        //Top shooter
        components.topShooterMotor.set(1);
        double topShooter = components.topShooterMotor.getSelectedSensorVelocity();
        System.out.println("Top Shooter: "+topShooter);
        components.topShooterMotor.stopMotor();

        //Bottom Shooter
        components.bottomShooterMotor.set(1);
        double bottomShooter = components.bottomShooterMotor.getSelectedSensorVelocity();
        System.out.println("Bottom Shooter: "+bottomShooter);
        components.bottomShooterMotor.stopMotor();

        //Left forward
        components.drive.tankDrive(1, 0);
        double leftFront = components.leftFrontDriveMotor.getOutputCurrent();
        double leftFollower = components.leftFollowerDriveMotor.getOutputCurrent();
        System.out.println("Left Front: "+leftFront+" Left Follow: "+leftFollower);
        components.drive.stopMotor();

        //Left Reverse
        components.drive.tankDrive(-1, 0);
        double leftFrontReverse = components.leftFrontDriveMotor.getOutputCurrent();
        double leftFollowerReverse = components.leftFollowerDriveMotor.getOutputCurrent();
        System.out.println("Left Front Reverse: "+leftFrontReverse+" Left Follow Reverse: "+leftFollowerReverse);
        components.drive.stopMotor();

        //Right Forward
        components.drive.tankDrive(0, 1);
        double rightFront = components.rightFrontDriveMotor.getOutputCurrent();
        double rightFollower = components.rightFollowerDriveMotor.getOutputCurrent();
        System.out.println("Right Front: "+rightFront +" Right Follow: "+rightFollower);
        components.drive.stopMotor();

        //Right Reverse
        components.drive.tankDrive(0, -1);
        double rightFrontReverse = components.rightFrontDriveMotor.getOutputCurrent();
        double rightFollowerReverse = components.rightFollowerDriveMotor.getOutputCurrent();
        System.out.println("Right Front Reverse: "+rightFrontReverse +"Right Follow Reverse: "+ rightFollowerReverse);
        components.drive.stopMotor();

        //Telescope
        components.telescopeMotor.set(1);
        double telescope = components.telescopeMotor.getSelectedSensorVelocity();
        System.out.println("telescope: "+telescope);
        components.telescopeMotor.stopMotor();

        //navx
        float navx = components.navx.getPitch();
        System.out.println("navx: "+navx);

        //Intake laser
        boolean intakeLaser = components.intakeLaserSensor.get();
        System.out.println("Intake Laser: "+intakeLaser);



    }
}
