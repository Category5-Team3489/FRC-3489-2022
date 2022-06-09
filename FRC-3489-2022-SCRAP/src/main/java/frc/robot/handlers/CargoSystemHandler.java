package frc.robot.handlers;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.Constants;
import frc.robot.framework.RobotHandler;
import frc.robot.handlers.ShuffleboardHandler.Tab;
import frc.robot.handlers.ShuffleboardHandler.Widget;

public class CargoSystemHandler extends RobotHandler {

    private boolean isIntakeActivated = false;
    private boolean isUnderManualControl = false;
   
    private int cargoCount = 0;

    private boolean stopShooterTimerRunning = false;
    private Timer stopShooterTimer = new Timer();

    private boolean ballInLaser = false;

    private Widget cargoCountWidget;


    @Override
    public void robotInit() {
        cargoCountWidget = shuffleboard.newWidget(Tab.Main, "Cargo Count", cargoCount, w -> {
            w.withPosition(0, 2);
        });
    }

    public void teleopPeriodic() {
        boolean isCargoInLaser = intake.isCargoInLaserSensor();

        if (autoShoot.isAutoShooting()) {
            return;
        }

        // TODO spam setting stuff
        // TODO Dont run if climbing
        /*
        if (climber.isClimbing()) {
            isIntakeActivated = false;
            isUnderManualControl = false;
            intake.stop();
            cargoTransfer.stop();
            shooter.stop();
            return;
        }
        */

        // TODO Add shuffleboard option to control
        if (cargoCount >= 2 && limelight.isTargetVisible() && shooter.isStopped()) {
            shooter.setShooterAtDistance(40);
        }

        isUnderManualControl = manualCargoSystem();

        toggleIntake();
        indexConveyorIfCargoInLaserSensor(isCargoInLaser);

        if (!isUnderManualControl) {
            shoot();
        }

        // Manual shooter control
        stop();
        lowHub();
        highHub();
        ejectCargo();
    }

    /*@Override
    public void setShuffleboardState() {
        shuffleboardHandler.setNumber(true, "Cargo Count", cargoCount);
    }*/

    public void toggleIntake() {
        boolean shouldToggleIntake = components.manipulatorJoystick.getRawButtonPressed(Constants.Buttons.Manipulator.ToggleIntake);
        if(shouldToggleIntake) {
            isIntakeActivated = !isIntakeActivated;
            if(isIntakeActivated) {
                intake.setForward(true);
                intake.set(IntakeHandler.State.Forward);
            }
            else {
                intake.setStopped();
                intake.set(IntakeHandler.State.Disabled);
            }
        }
    }

    private void indexConveyorIfCargoInLaserSensor(boolean isCargoInLaser) {
        if (!isIntakeActivated) {
            return;
        }
        if (isCargoInLaser && !ballInLaser) {
            if(cargoCount == 0) {
                ballInLaser = true;
                setCargoCount(1);
                cargoTransfer.setForward();
                ballInLaser = false;
            }
            else if (cargoCount == 1 && !cargoTransfer.isIndexing()) {
                ballInLaser = true;
                setCargoCount(2);
            }
        }
    }

    private boolean manualCargoSystem() {
        double manipulatorJoystick = components.manipulatorJoystick.getY();
        if(manipulatorJoystick > Constants.CargoTransfer.ManualCargoSystemControlThreshhold) {
            intake.set(IntakeHandler.State.Reverse);
            cargoTransfer.set(Constants.CargoTransfer.ReverseIndexMotorSpeed);
        }
        else if (manipulatorJoystick < -Constants.CargoTransfer.ManualCargoSystemControlThreshhold) {

            intake.set(IntakeHandler.State.Forward);
            cargoTransfer.set(Constants.CargoTransfer.ForwardIndexMotorSpeed);;
        }
        else {
            if (isIntakeActivated) {
                intake.setForward(true);
            }
            else {
                intake.setStopped();
            }
            cargoTransfer.stopIfNotIndexing();
            return false;
        }

        return true;
    }

    public void setCargoCount(int desired) {
        if (cargoCount != desired) {
            cargoCount = desired;
        }
    }
    
    private void shoot() {
        boolean shouldShoot = components.manipulatorJoystick.getRawButton(Constants.Buttons.Manipulator.Shoot);
        
        if (shouldShoot) {
            if (!stopShooterTimerRunning) {
                stopShooterTimer.start();
                stopShooterTimerRunning = true;
            }
            stopShooterTimer.reset();
            cargoTransfer.setShootSpeed();
            setCargoCount(0);
            ballInLaser = false;
        }
        else {
            cargoTransfer.stopIfNotIndexing();
        }
        if (stopShooterTimerRunning && stopShooterTimer.hasElapsed(Constants.Shooter.ShootStopTimeDelay)) {
            stopShooterTimer.stop();
            stopShooterTimer.reset();
            stopShooterTimerRunning = false;
            shooter.setStopped();
        }
    }

    private void stop() {
        boolean isPressed = components.manipulatorJoystick.getRawButtonPressed(Constants.Buttons.Manipulator.SetStopped);
        if (isPressed) {
            shooter.setStopped();
        }
    }

    private void lowHub() {
        boolean isPressed = components.manipulatorJoystick.getRawButtonPressed(Constants.Buttons.Manipulator.SetLowHub);
        if (isPressed) {
            shooter.setLowHub();
        }
    }

    private void highHub() {
        boolean isPressed = components.manipulatorJoystick.getRawButtonPressed(Constants.Buttons.Manipulator.SetHighHub);
        if (isPressed) {
            shooter.setHighHub();
        }
    }

    private void ejectCargo() {
        boolean isPressed = components.manipulatorJoystick.getRawButtonPressed(Constants.Buttons.Manipulator.SetEjectCargo);
        if (isPressed) {
            shooter.setEjectCargo();
        }
    }
}
