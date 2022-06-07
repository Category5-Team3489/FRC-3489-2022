package frc.robot.handlers;

import frc.robot.Constants;
import frc.robot.framework.RobotHandler;
import frc.robot.handlers.ShuffleboardHandler.Tab;
import frc.robot.handlers.ShuffleboardHandler.Widget;

public class CargoSystemHandler extends RobotHandler {
    // TODO implement

    

    private boolean isIntakeActivated = false;

    private boolean isCargoInLaser = intake.isCargoInLaserSensor();
    private int cargoCount = 0;
    private boolean ballInLaser = false;

    private boolean isUnderManualControl = manualCargoSystem();

    @Override
    public void robotInit() {
        
    }

    public void teleopPeriodic() {
        
    }

    public void ToggleIntake() {
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
                cargoTransfer.forwardIndex();
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

            intake.set(IntakeHandler.State.Forward)
            cargoTransfer.set(Constants.CargoTransfer.ForwardIndexMotorSpeed);;
        }
        else {
            if (isIntakeActivated) {
                intake
.setForward(true);
            }

            else 
                intake.setStopped();
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

    
}
