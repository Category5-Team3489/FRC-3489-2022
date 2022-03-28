package frc.robot.handlers;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.Constants;
import frc.robot.framework.RobotHandler;
import frc.robot.interfaces.IShuffleboardState;

public class CargoSystemHandler extends RobotHandler implements IShuffleboardState {

    private boolean isIntakeActivated = false;
    private boolean isUnderManualControl = false;

    private int cargoCount = 0;

    private Timer stopShooterTimer = new Timer();
    private boolean stopShooterTimerRunning = false;
    
    @Override
    public void teleopPeriodic() {

        boolean isCargoInLaser = intakeHandler.isCargoInLaser();

        // TODO spams setting stuff
        if (climberHandler.isClimbing()) {
            isIntakeActivated = false;
            isUnderManualControl = false;
            intakeHandler.stop();
            cargoTransferHandler.stop();
            shooterHandler.stop();
            return;
        }

        isUnderManualControl = manualCargoSystem();

        toggleIntake();
        indexConveyorIfCargoInLaserSensor(isCargoInLaser);
        
        if (cargoCount == 2 && isCargoInLaser) {
            if (!isUnderManualControl) {
                isIntakeActivated = false;
                intakeHandler.stop();
            }
        }

        if (!isUnderManualControl) {
            shoot();
        }

        stopShooter();
        shootLowGoal();
        shootHighGoal();
        shootWrongColor();
    }

    @Override
    public void setShuffleboardState() {
        shuffleboardHandler.setNumber(true, "Cargo Count", cargoCount);
    }

    private void shoot() {
        boolean shouldShoot = components.manipulatorJoystick.getRawButton(Constants.ButtonShoot);
        
        if (shouldShoot) {
            if (!stopShooterTimerRunning) {
                stopShooterTimer.start();
                stopShooterTimerRunning = true;
            }
            stopShooterTimer.reset();
            cargoTransferHandler.setShootSpeed();
            setCargoCount(0);
        }
        else {
            cargoTransferHandler.stopIfNotIndexing();
        }
        if (stopShooterTimerRunning && stopShooterTimer.hasElapsed(Constants.ShootStopTimeDelay)) {
            stopShooterTimer.stop();
            stopShooterTimer.reset();
            stopShooterTimerRunning = false;
            shooterHandler.stop();
        }
    }

    private void indexConveyorIfCargoInLaserSensor(boolean isCargoInLaser) {
        if (!isIntakeActivated || isUnderManualControl)
            return;

        if (isCargoInLaser) {
            if (!cargoTransferHandler.isIndexing()) {
                setCargoCount(cargoCount + 1);
                cargoTransferHandler.index();
            }
        }
    }

    private void toggleIntake() {
        if (isUnderManualControl)
            return;
        boolean shouldToggleIntake = components.manipulatorJoystick.getRawButtonPressed(Constants.ButtonToggleIntake);
        if (shouldToggleIntake) {
            isIntakeActivated = !isIntakeActivated;
            if (isIntakeActivated) {
                intakeHandler.forwardIntake(true);
            }
            else {
                intakeHandler.stop();
            }
        }
    }

    private void stopShooter() {
        boolean isPressed = components.manipulatorJoystick.getRawButtonPressed(Constants.ButtonStopShooter);
        if (isPressed) {
            shooterHandler.stop();
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
        boolean isPressed = components.manipulatorJoystick.getRawButtonPressed(Constants.ButtonShootWrongColor);
        if (isPressed) {
            shooterHandler.setWrongColor();
        }
    }
    
    private boolean manualCargoSystem() {
        double manipulatorJoystick = components.manipulatorJoystick.getY();
        if (manipulatorJoystick > Constants.ManualCargoSystemControlThreshhold) {
            intakeHandler.backwardIntake(false);
            cargoTransferHandler.set(Constants.ReverseCargoTransferMotorSpeed);
        }
        else if (manipulatorJoystick < -Constants.ManualCargoSystemControlThreshhold) {
            intakeHandler.forwardIntake(false);
            cargoTransferHandler.set(Constants.CargoTransferMotorSpeed);
        }
        else {
            cargoTransferHandler.stopIfNotIndexing();
            return false;
        }
        return true;
    }

    private void setCargoCount(int desired) {
        if (cargoCount != desired) {
            cargoCount = desired;
            setShuffleboardState();
        }
    }

}
