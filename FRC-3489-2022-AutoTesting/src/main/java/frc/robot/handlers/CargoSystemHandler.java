package frc.robot.handlers;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.Constants;
import frc.robot.framework.RobotHandler;

public class CargoSystemHandler extends RobotHandler {

    private boolean isIntakeActivated = false;
    private int cargoCount = 0;
    private boolean isUnderManualControl = false;
    
    @Override
    public void teleopPeriodic() {

        if (climberHandler.isClimbing()) {
            intakeHandler.stopIntake();
            isIntakeActivated = false;
            cargoTransferHandler.set(0);
            shooterHandler.stopShooter();
            return;
        }

        isUnderManualControl = manualIntakeAndCargoTransfer();

        toggleIntake();
        indexConveyorIfCargoInLaserSensor();
        if (cargoCount == 2 && intakeHandler.isCargoInLaser()) {
            if (!isUnderManualControl) {
                isIntakeActivated = false;
                intakeHandler.stopIntake();
            }
        }

        if (!isUnderManualControl) {
            shoot();
        }

        shootLowGoal();
        shootHighGoal();
        shootWrongColor();
        stopShooter();

        shuffleboardHandler.showNumber(true, "Cargo Count", cargoCount);
    }

    private Timer shootTimer = new Timer();
    private boolean shootTimerRunning = false;

    private void shoot() {
        /*
        if (!shooterHandler.canShoot()) {
            cargoTransferHandler.stopIfNotIndexing();
            return;
        }
        */
        boolean shoot = components.manipulatorJoystick.getRawButton(Constants.ButtonShoot);
        //System.out.println(shoot ? "T" : "F");
        //if(buttonHandler.shootUnpressed())
            //shooterHandler.stopShooter();
        
        if (shoot) {
            if (!shootTimerRunning) {
                shootTimer.start();
                shootTimerRunning = true;
            }
            shootTimer.reset();
            cargoTransferHandler.setShootSpeed();
            cargoCount = 0;
        }
        else {
            cargoTransferHandler.stopIfNotIndexing();
        }
        if (shootTimerRunning && shootTimer.hasElapsed(Constants.ShootStopTimeDelay)) {
            shootTimer.stop();
            shootTimer.reset();
            shootTimerRunning = false;
            shooterHandler.stopShooter();
        }
        /* if(buttonHandler.shootUnpressed())
            shooterHandler.stopShooter();
            cargoTransferHandler.stopIfNotIndexing();
        
        if (shoot) {
            cargoTransferHandler.setShootSpeed();
            cargoCount = 0;
        }
        else {
            //cargoTransferHandler.stopIfNotIndexing();
        } */
    }

    private void indexConveyorIfCargoInLaserSensor() {
        if (!isIntakeActivated || isUnderManualControl)
            return;
        
        boolean isCargoInLaser = intakeHandler.isCargoInLaser();
        if (isCargoInLaser) {
            if (!cargoTransferHandler.isIndexing()) {
                cargoCount++;
                cargoTransferHandler.index();
            }
        }
    }

    private void toggleIntake() {
        //if (buttonHandler.toggleIntakePressed())

        if (isUnderManualControl)
            return;

        if (components.manipulatorJoystick.getRawButtonPressed(Constants.ButtonToggleIntake)) {
            isIntakeActivated = !isIntakeActivated;
            if (isIntakeActivated)
                intakeHandler.startIntake();
            else
                intakeHandler.stopIntake();
            shuffleboardHandler.setBoolean(true, "Intake Running", isIntakeActivated);
        }
    }

    private void shootLowGoal() {
        boolean isPressed = components.manipulatorJoystick.getRawButtonPressed(Constants.ButtonShootLowGoal);
        if (isPressed) {
            shooterHandler.shootLow();
        }
    }

    private void shootHighGoal() {
        boolean isPressed = components.manipulatorJoystick.getRawButtonPressed(Constants.ButtonShootHighGoal);
        if (isPressed) {
            shooterHandler.shootHigh();
        }
    }

    private void shootWrongColor() {
        boolean isPressed = components.manipulatorJoystick.getRawButton(Constants.ButtonShootWrongColor);
        if (isPressed) {
            shooterHandler.setWrongColor();
        }
    }

    private void stopShooter() {
        boolean isPressed = components.manipulatorJoystick.getRawButtonPressed(Constants.ButtonStopShooter);
        if (isPressed) {
            shooterHandler.stopShooter();
        }
    }
    

    private boolean manualIntakeAndCargoTransfer() {
        double manipulatorJoystick = components.manipulatorJoystick.getY();
        if (manipulatorJoystick > Constants.ManualCargoSystemControlThreshhold) {
            intakeHandler.reverseIntake();
            shuffleboardHandler.showBoolean(true, "Intake Running", true);
            cargoTransferHandler.set(Constants.ReverseCargoTransferMotorSpeed);
        }
        else if (manipulatorJoystick < -Constants.ManualCargoSystemControlThreshhold) {
            intakeHandler.startIntake();
            shuffleboardHandler.showBoolean(true, "Intake Running", true);
            cargoTransferHandler.set(Constants.CargoTransferMotorSpeed);
        }
        else {
            shuffleboardHandler.showBoolean(true, "Intake Running", isIntakeActivated);
            cargoTransferHandler.stopIfNotIndexing();
            return false;
        }
        return true;
    }

}
