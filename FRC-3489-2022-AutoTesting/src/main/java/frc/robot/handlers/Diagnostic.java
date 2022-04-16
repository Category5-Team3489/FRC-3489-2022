package frc.robot.handlers;

import frc.robot.framework.RobotHandler;
import frc.robot.Constants;
import frc.robot.auto.framework.AutoBuilder;

public class Diagnostic extends RobotHandler {

    public boolean isIntakeFinished = false;
    public boolean isCargoTransferFinished = false;
    public boolean isTopShooterFinished = false;
    public boolean isBottomShooterFinished = false;
    public boolean isLeftForwardFinished = false;
    public boolean isLeftReverseFinished = false;
    public boolean isRightForwardFinished = false;
    public boolean isRightReverseFinished = false;
    public boolean isTelescopeFinished = false;
    public boolean isnavxFinished = false;
    public boolean isIntakeLaserFinished = false;

    public boolean intake() {
        components.intakeMotor.set(1);
        double intake = components.intakeMotor.getSelectedSensorVelocity();
        components.intakeMotor.stopMotor();
        if(intake == Constants.Diagnostic.IntakeVelocity){
            return true;
        }
        return false;
    }

    public boolean cargoTransfer() {
        components.cargoTransferMotor.set(1);
        double cargoTransfer = components.cargoTransferMotor.getSelectedSensorVelocity();
        components.cargoTransferMotor.stopMotor();
        if(cargoTransfer == Constants.Diagnostic.CargoTransferVelocity){
            return true;
        }
        return false;
    }

    public boolean topShooter() {
        components.topShooterMotor.set(1);
        double topShooter = components.topShooterMotor.getSelectedSensorVelocity();
        components.topShooterMotor.stopMotor();
        if(topShooter == Constants.Diagnostic.TopShooterVelocity){
            return true;
        }
        return false;
    }
    
    public boolean bottomShooter() {
        components.bottomShooterMotor.set(1);
        double bottomShooter = components.bottomShooterMotor.getSelectedSensorVelocity();
        components.bottomShooterMotor.stopMotor();
        if(bottomShooter == Constants.Diagnostic.BottomShooterVelocity){
            return true;
        }
        return false;
    }

    public boolean leftDrive() {
        components.drive.tankDrive(1, 0);
        double leftFront = components.leftFrontDriveMotor.getOutputCurrent();
        double leftFollow = components.leftFollowerDriveMotor.getOutputCurrent();
        components.drive.stopMotor();
        if(leftFront == Constants.Diagnostic.LeftFrontForward && leftFollow == Constants.Diagnostic.LeftFollowForward){
            return true;
        }
        return false;
    }

    public boolean leftDriveReverse() {
        components.drive.tankDrive(-1, 0);
        double leftFront = components.leftFrontDriveMotor.getOutputCurrent();
        double leftFollow = components.leftFollowerDriveMotor.getOutputCurrent();
        components.drive.stopMotor();
        if(leftFront == Constants.Diagnostic.LeftFrontReverse && leftFollow == Constants.Diagnostic.LeftFollowReverse){
            return true;
        }
        return false;
    }

    public boolean rightDrive() {
        components.drive.tankDrive(0, 1);
        double rightFront = components.rightFrontDriveMotor.getOutputCurrent();
        double rightFollow = components.rightFollowerDriveMotor.getOutputCurrent();
        components.drive.stopMotor();
        if(rightFront == Constants.Diagnostic.RightFrontForward && rightFollow == Constants.Diagnostic.RightFollowForward){
            return true;
        }
        return false;
    }

    public boolean rightDriveReverse() {
        components.drive.tankDrive(0, -1);
        double rightFront = components.rightFrontDriveMotor.getOutputCurrent();
        double rightFollow = components.rightFollowerDriveMotor.getOutputCurrent();
        components.drive.stopMotor();
        if(rightFront == Constants.Diagnostic.RightFrontReverse && rightFollow == Constants.Diagnostic.RightFollowReverse){
            return true;
        }
        return false;
    }

    public boolean telescope() {
        components.telescopeMotor.set(1);
        double telescope = components.telescopeMotor.getSelectedSensorVelocity();
        components.telescopeMotor.stopMotor();
        if(telescope == Constants.Diagnostic.TelescopeVelocity){
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


    //Errors
    public boolean intakeError() {
        intake();
        boolean intake = intake();
        if(intake = false){
            System.out.println("Intake Error");
        }
        return true;
    }

    public void cargoTransferError(){
        cargoTransfer();
        boolean cargoTransfer = cargoTransfer();
        if(cargoTransfer = false){
            System.out.println("Cargo Transfer Error");
        }
    }

    public void topShooterError(){
        topShooter();
        boolean topShooter = topShooter();
        if(topShooter = false){
            System.out.println("Top Shooter Error");
        }
    }

    public void bottomShooterError(){
        bottomShooter();
        boolean bottomShooter = bottomShooter();
        if(bottomShooter = false){
            System.out.println("Bottom Shooter Error");
        }
    }

    public void leftDriveForwardError(){
        leftDrive();
        boolean leftDrive = leftDrive();
        if(leftDrive = false){
            System.out.println("Left Drive Forward Error");
        }
    }

    public void leftDriveReverseError(){
        leftDriveReverse();
        boolean leftReverse = leftDriveReverse();
        if(leftReverse = false){
            System.out.println("Left Drive Reverse Error");
        }
    }

    public void rightDriveForwardError(){
        rightDrive();
        boolean rightForward = rightDrive();
        if(rightForward = false){
            System.out.println("Right Drive Forward Error");
        }
    }

    public void RightDriveReverseError(){
        RightDriveReverseError();
        boolean rightReverse = rightDriveReverse();
        if(rightReverse = false){
            System.out.println("Right Drive Reverse Error");
        }
    }

    public void telescopeError(){
        telescope();
        boolean telescope = telescope();
        if(telescope = false){
            System.out.println("Telescope Error");
        }
    }

    public void navxError(){
        navx();
        boolean navx = navx();
        if(navx = false){
            System.out.println("Navx Error");
        }
    }

    public void intakeLaserError(){
        intakeLaser();
        boolean intakeLaser = intakeLaser();
        if(intakeLaser = false){
            System.out.println("Intake Laser Error");
        }
    }
    

   
    public void getBaseLine(){
        //Intake
        components.intakeMotor.set(1);
        AutoBuilder.pause(1);
        double intake = components.intakeMotor.getSelectedSensorVelocity();
        System.out.println("intake: "+intake);
        components.intakeMotor.stopMotor();
        isIntakeFinished = true;

        //Cargo Transfer
        if(isIntakeFinished){
            components.cargoTransferMotor.set(1);
            AutoBuilder.pause(1);
            double cargoTransfer = components.cargoTransferMotor.getSelectedSensorVelocity();
            System.out.println("Cargo Transfer: "+cargoTransfer);
            components.cargoTransferMotor.stopMotor();
            isCargoTransferFinished = true;
        }

        //Top shooter
        if(isCargoTransferFinished){
            components.topShooterMotor.set(1);
            AutoBuilder.pause(1);
            double topShooter = components.topShooterMotor.getSelectedSensorVelocity();
            System.out.println("Top Shooter: "+topShooter);
            components.topShooterMotor.stopMotor();
            isTopShooterFinished = true;
        }

        //Bottom Shooter
        if(isTopShooterFinished){
            components.bottomShooterMotor.set(1);
            AutoBuilder.pause(1);
            double bottomShooter = components.bottomShooterMotor.getSelectedSensorVelocity();
            System.out.println("Bottom Shooter: "+bottomShooter);
            components.bottomShooterMotor.stopMotor();
            isBottomShooterFinished = true;
        }
        

        //Left forward
        if(isBottomShooterFinished){
            components.drive.tankDrive(1, 0);
            AutoBuilder.pause(1);
            double leftFront = components.leftFrontDriveMotor.getOutputCurrent();
            double leftFollower = components.leftFollowerDriveMotor.getOutputCurrent();
            System.out.println("Left Front: "+leftFront+" Left Follow: "+leftFollower);
            components.drive.stopMotor();
            isLeftForwardFinished = true;
        }
        

        //Left Reverse
        if(isLeftForwardFinished){
            components.drive.tankDrive(-1, 0);
            AutoBuilder.pause(1);
            double leftFrontReverse = components.leftFrontDriveMotor.getOutputCurrent();
            double leftFollowerReverse = components.leftFollowerDriveMotor.getOutputCurrent();
            System.out.println("Left Front Reverse: "+leftFrontReverse+" Left Follow Reverse: "+leftFollowerReverse);
            components.drive.stopMotor();
            isLeftReverseFinished = true;
        }
        

        //Right Forward
        if(isLeftReverseFinished){
            components.drive.tankDrive(0, 1);
            AutoBuilder.pause(1);
            double rightFront = components.rightFrontDriveMotor.getOutputCurrent();
            double rightFollower = components.rightFollowerDriveMotor.getOutputCurrent();
            System.out.println("Right Front: "+rightFront +" Right Follow: "+rightFollower);
            components.drive.stopMotor();
            isRightForwardFinished = true;
        }
        

        //Right Reverse
        if(isRightForwardFinished){
            components.drive.tankDrive(0, -1);
            AutoBuilder.pause(1);
            double rightFrontReverse = components.rightFrontDriveMotor.getOutputCurrent();
            double rightFollowerReverse = components.rightFollowerDriveMotor.getOutputCurrent();
            System.out.println("Right Front Reverse: "+rightFrontReverse +"Right Follow Reverse: "+ rightFollowerReverse);
            components.drive.stopMotor();
            isRightReverseFinished = true;
        }
        

        //Telescope
        if(isRightReverseFinished){
            components.telescopeMotor.set(1);
            AutoBuilder.pause(1);
            double telescope = components.telescopeMotor.getSelectedSensorVelocity();
            System.out.println("telescope: "+telescope);
            components.telescopeMotor.stopMotor();
            isTelescopeFinished = true;
        }
        

        //navx
        if(isTelescopeFinished){
            float navx = components.navx.getPitch();
            System.out.println("navx: "+navx);
            isnavxFinished = true;
        }

        //Intake laser
        if(isnavxFinished){
            boolean intakeLaser = components.intakeLaserSensor.get();
            System.out.println("Intake Laser: "+intakeLaser);
            isIntakeLaserFinished = true;
        }
    
    }
}
