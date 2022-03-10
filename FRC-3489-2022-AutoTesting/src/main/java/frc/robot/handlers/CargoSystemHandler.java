package frc.robot.handlers;

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

        if (cargoCount < 2) {
            toggleIntake();
            indexConveyorIfCargoInLaserSensor();
        }
        else {
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

    private void shoot() {
        if (!shooterHandler.canShoot()) {
            cargoTransferHandler.stopIfNotIndexing();
            return;
        }
        boolean shoot = components.manipulatorJoystick.getRawButton(Constants.ButtonShoot);

        if(buttonHandler.shootUnpressed())
            shooterHandler.stopShooter();
            cargoTransferHandler.stopIfNotIndexing();
        
        if (shoot) {
            cargoTransferHandler.setShootSpeed();
            cargoCount = 0;
        }
        else {
            //cargoTransferHandler.stopIfNotIndexing();
        }
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
        if (buttonHandler.toggleIntakePressed())
            isIntakeActivated = !isIntakeActivated;

        if (isUnderManualControl)
            return;

        if (isIntakeActivated)
            intakeHandler.startIntake();
        else
            intakeHandler.stopIntake();
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
            cargoTransferHandler.set(Constants.ReverseCargoTransferMotorSpeed);
        }
        else if (manipulatorJoystick < -Constants.ManualCargoSystemControlThreshhold) {
            intakeHandler.startIntake();
            cargoTransferHandler.set(Constants.CargoTransferMotorSpeed);
        }
        else {
            cargoTransferHandler.stopIfNotIndexing();
            return false;
        }
        return true;
    }

}
