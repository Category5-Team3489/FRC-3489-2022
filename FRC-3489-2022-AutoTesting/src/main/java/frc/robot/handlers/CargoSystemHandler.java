package frc.robot.handlers;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.Constants;
import frc.robot.framework.RobotHandler;
import frc.robot.interfaces.IShuffleboardState;
import frc.robot.types.DriveState;

public class CargoSystemHandler extends RobotHandler implements IShuffleboardState {

    private boolean isIntakeActivated = false;
    private boolean isUnderManualControl = false;

    private int cargoCount = 0;

    private Timer stopShooterTimer = new Timer();
    private boolean stopShooterTimerRunning = false;

    private boolean ballInLaser = false;
    
    //private Timer oneBallDelay = new Timer();
    
    @Override
    public void teleopPeriodic() {

        boolean isCargoInLaser = intakeHandler.isCargoInLaser();

        if (driveHandler.getDriveState() != DriveState.Driving) {
            return;
        }

        // TODO spam setting stuff
        if (climberHandler.isClimbing()) {
            isIntakeActivated = false;
            isUnderManualControl = false;
            intakeHandler.stop();
            cargoTransferHandler.stop();
            shooterHandler.stop();
            return;
        }

        if (cargoCount >= 2 && limelightHandler.isTargetVisible() && shooterHandler.isShooterStopped() && shuffleboardHandler.isShooterSpinupEnabled()) {
            shooterHandler.setShooterAtDistance(60);
            /*
            double de = driveHandler.getDistanceEstimate();
            if (de == -1) {
                shooterHandler.setShooterAtDistance(60);
            }
            else {
                shooterHandler.setShooterAtDistance(driveHandler.getDistanceEstimate());
            }
            */
        }

        isUnderManualControl = manualCargoSystem();

        toggleIntake();
        indexConveyorIfCargoInLaserSensor(isCargoInLaser);
        
        /*
        if (cargoCount == 2 && isCargoInLaser) {
            if (!isUnderManualControl) {
                isIntakeActivated = false;
                intakeHandler.stop();
            }
        }
        */

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
        boolean shouldShoot = components.manipulatorJoystick.getRawButton(Constants.Buttons.Shoot);
        
        if (shouldShoot) {
            if (!stopShooterTimerRunning) {
                stopShooterTimer.start();
                stopShooterTimerRunning = true;
            }
            stopShooterTimer.reset();
            cargoTransferHandler.setShootSpeed();
            setCargoCount(0);
            ballInLaser = false;
        }
        else {
            cargoTransferHandler.stopIfNotIndexing();
        }
        if (stopShooterTimerRunning && stopShooterTimer.hasElapsed(Constants.Shooter.ShootStopTimeDelay)) {
            stopShooterTimer.stop();
            stopShooterTimer.reset();
            stopShooterTimerRunning = false;
            shooterHandler.stop();
        }
    }


    private void indexConveyorIfCargoInLaserSensor(boolean isCargoInLaser) {
        if (!isIntakeActivated || isUnderManualControl)
            return;

        if (isCargoInLaser && !ballInLaser) {
            
            if (cargoCount == 0) {
                ballInLaser = true;
                setCargoCount(1);
                cargoTransferHandler.index();
                ballInLaser = false;
            }
            else if (cargoCount == 1 && !cargoTransferHandler.isIndexing()) {
                ballInLaser = true;
                setCargoCount(cargoCount + 1);
                //isIntakeActivated = false;
                //intakeHandler.stop();
            }
            /*
            if (cargoCount == 0) {
                setCargoCount(1);
                oneBallDelay.reset();
                oneBallDelay.start();
            }
            else if (cargoCount >= 1 && oneBallDelay.hasElapsed(1) && !cargoTransferHandler.isIndexing()) {
                setCargoCount(cargoCount + 1);
                cargoTransferHandler.index();
                isIntakeActivated = true;
                intakeHandler.forwardIntake(false);
            }
            */
            /*
            if (cargoCount < 2 && !cargoTransferHandler.isIndexing()) {
                setCargoCount(cargoCount + 1);
                cargoTransferHandler.index();
            }
            */
        }
    }

    private void toggleIntake() {
        if (isUnderManualControl)
            return;
        boolean shouldToggleIntake = components.manipulatorJoystick.getRawButtonPressed(Constants.Buttons.ToggleIntake);
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
        boolean isPressed = components.manipulatorJoystick.getRawButtonPressed(Constants.Buttons.StopShooter);
        if (isPressed) {
            shooterHandler.stop();
        }
    }
    private void shootLowGoal() {
        boolean isPressed = components.manipulatorJoystick.getRawButtonPressed(Constants.Buttons.ShootLowGoal);
        if (isPressed) {
            shooterHandler.shootLow();
        }
    }
    private void shootHighGoal() {
        boolean isPressed = components.manipulatorJoystick.getRawButtonPressed(Constants.Buttons.ShootHighGoal);
        if (isPressed) {
            shooterHandler.shootHigh();
        }
    }
    private void shootWrongColor() {
        boolean isPressed = components.manipulatorJoystick.getRawButtonPressed(Constants.Buttons.ShootWrongColor);
        if (isPressed) {
            shooterHandler.setWrongColor();
        }
    }
    
    private boolean manualCargoSystem() {
        double manipulatorJoystick = components.manipulatorJoystick.getY();
        if (manipulatorJoystick > Constants.ManualCargoSystemControlThreshhold) {
            intakeHandler.backwardIntake(false);
            cargoTransferHandler.set(Constants.Speeds.ReverseCargoTransferMotorSpeed);
        }
        else if (manipulatorJoystick < -Constants.ManualCargoSystemControlThreshhold) {
            intakeHandler.forwardIntake(false);
            cargoTransferHandler.set(Constants.Speeds.CargoTransferMotorSpeed);
        }
        else {
            if (isIntakeActivated)
                intakeHandler.forwardIntake(true);
            else
                intakeHandler.stop();
            cargoTransferHandler.stopIfNotIndexing();
            return false;
        }
        return true;
    }

    public void setCargoCount(int desired) {
        if (cargoCount != desired) {
            cargoCount = desired;
            setShuffleboardState();
        }
    }

}
