package frc.robot.handlers;

import frc.robot.Constants;
import frc.robot.framework.RobotHandler;

public class CargoSystemHandler extends RobotHandler {

    private boolean isIntakeActivated = false;
    private int cargoCount = 0;
    
    @Override
    public void teleopPeriodic() {

        // Joystick Inputs
        // Done: POV knob controls cargo transfer conveyor manually and set intake also
        boolean isUnderManualControl = manualIntakeAndCargoTransfer();

        // If 2 balls inside stop intaking
        if (cargoCount < 2) {
            // Toggle button for activating intake probably trigger on manipulator joystick
            toggleIntake(isUnderManualControl);

            // Index cargo transfer conveyor when ball is in laser sensor
            indexConveyorIfCargoInLaserSensor();
        }
        else {
            if (!isUnderManualControl) {
                isIntakeActivated = false;
                intakeHandler.stopIntake();
            }
        }

        // While manipulator joystick trigger is pressed move cargo from conveyor into shooter wheels
        if (!isUnderManualControl) {
            shoot();
        }


        // Wait on: Wrong color detection and correction
        wrongColorShooter();

        // Push button for setting shooter to low goal speed Mackenzie
        lowGoalShooter();
        // Push button for setting shooter to high goal speed Alex
        highGoalShooter();
        // Push button to stop shooter Afif
        stopShooter();

        shuffleboardHandler.showNumber(true, "Cargo Count", cargoCount);
    }

    private void shoot() {

        if (!shooterHandler.canShoot()) {
            cargoTransferHandler.stopIfNotIndexing();
            return;
        }
        
        boolean shoot = components.manipulatorJoystick.getRawButtonPressed(Constants.ShootButton);
        if (shoot) {
            cargoTransferHandler.setShootSpeed();
            // reset cargoCount somewhere
            cargoCount = 0;
        }
        else {
            cargoTransferHandler.stopIfNotIndexing();
        }
    }
    
    private void indexConveyorIfCargoInLaserSensor() {

        if (!isIntakeActivated) return; // may also or only want to ensure is not under manual control

        // laser sensor input
        boolean isCargoInLaser = intakeHandler.isCargoInLaser();
        if (isCargoInLaser) {
            if (!cargoTransferHandler.isIndexing()) {
                cargoCount++;
                cargoTransferHandler.index();
            }
        }
    }

    private void toggleIntake(boolean isUnderManualControl) {
        // Toggle button for activating intake probably trigger on manipulator joystick

        boolean shouldToggleIntake = components.manipulatorJoystick.getRawButtonPressed(Constants.ToggleIntakeButton);
        if (shouldToggleIntake) {
            isIntakeActivated = !isIntakeActivated;
        }
        // Prevents running intake if under manual control
        if (isUnderManualControl) return;

        if (isIntakeActivated) {
            intakeHandler.startIntake();
        }
        else {
            intakeHandler.stopIntake();
        }

    }

    private void lowGoalShooter() {
        // Push button for setting shooter to low goal speed Mackenzie
        // Get button input from manipulator Joystick
        boolean shootLow = components.manipulatorJoystick.getRawButtonPressed(Constants.SetShooterLowGoalButton);
        // shoot low if button pressed
        if (shootLow) {
            shooterHandler.shootLow();
            // Set shoot low shuffleboard
            shuffleboardHandler.setString(true, "Shooter Mode", "Low");
        }
    }

    private void highGoalShooter() {
        // Push button for setting shooter to high goal speed Alex
        // Get button input from manipulator Joystick
        boolean shootHigh = components.manipulatorJoystick.getRawButtonPressed(Constants.SetShooterHighGoalButton);
        // shoot high if button pressed
        if (shootHigh) {
            shooterHandler.shootHigh();
            // Set shoot high shuffleboard
            shuffleboardHandler.setString(true, "Shooter Mode", "High");
        }
    }

    private void stopShooter() {
        // Push button to stop shooter Afif
        //get the input from the manipulator 
        boolean stopShooter = components.manipulatorJoystick.getRawButtonPressed(Constants.StopShooterButton);
        //stops the shooter if button pressed
        if (stopShooter) {
            shooterHandler.stopShooter();
            // Set shooter stopped shuffleboard
            shuffleboardHandler.setString(true, "Shooter Mode", "Stopped");
        }
    }

    private void wrongColorShooter() {
        //Push button to shoot wrong color
        //get the input from the mainipulator
        boolean wrongColor = components.manipulatorJoystick.getRawButtonPressed(Constants.WrongColorButton);
        //shoot wrong color at low speed if button is pressed
        if (wrongColor) {
            shooterHandler.wrongColor();
            //Set shooter Wrong color on shuffleboard
            shuffleboardHandler.setString(true, "Shooter Mode", "Wrong Color");
        }
    }
    

    private boolean manualIntakeAndCargoTransfer() {
        
        // set intake speed based on pov
        // set cargo transfer conveyor based on pov
        double manipulatorJoystick = components.manipulatorJoystick.getY();
        if (manipulatorJoystick > 0.5) { // manipulator is up
           // set intake speed to move up
            intakeHandler.startIntake();
            // set cargo transfer conveyor to move up
            cargoTransferHandler.set(Constants.CargoTransferMotorSpeed);
        }
        else if (manipulatorJoystick < -0.5) { // manipulator is down
            // set intake speed to move down
            intakeHandler.reverseIntake();
            // set cargo transfer conveyor to move down
            cargoTransferHandler.set(Constants.ReverseCargoTransferMotorSpeed);
        }
        else {
            // stopping motors
            cargoTransferHandler.stopIfNotIndexing();
            return false;
        }

        return true;
    }

}
