package frc.robot.handlers;

import frc.robot.Constants;
import frc.robot.containers.ComponentsContainer;

public class Diagnostic {

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
        double bottomShooter = components.topShooterMotor.getSelectedSensorVelocity();
        components.topShooterMotor.stopMotor();
        if(bottomShooter == 10){
            return true;
        }
        return false;
    }

    public double leftFront() {
        double leftFront = components.leftFrontDriveMotor.getOutputCurrent();
        return leftFront;
    }

    public double leftFollow() {
        double leftFollow = components.leftFollowerDriveMotor.getOutputCurrent();
        return leftFollow;
    }

    public double rightFront() {
        double rightfront = components.rightFrontDriveMotor.getOutputCurrent();
        return rightfront;
    }

    public double rightFollow() {
        double rightFollow = components.rightFollowerDriveMotor.getOutputCurrent();
        return rightFollow;
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

    public double navx() {
        double navx = components.navx.getPitch();
        return navx;
    }

    public boolean intakeLaser() {
        boolean intakeLaser = components.intakeLaserSensor.get();
        return intakeLaser;
    }
}
