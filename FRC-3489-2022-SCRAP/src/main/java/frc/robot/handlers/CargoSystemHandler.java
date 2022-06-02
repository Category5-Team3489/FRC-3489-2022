package frc.robot.handlers;

import frc.robot.Constants;
import frc.robot.framework.RobotHandler;
import frc.robot.handlers.ShuffleboardHandler.Tab;
import frc.robot.handlers.ShuffleboardHandler.Widget;

public class CargoSystemHandler extends RobotHandler {
    // TODO implement

    // 

    private boolean isIntakeActivated = false;

    private boolean isCargoInLaser = IntakeHandler.isCargoInLaserSensor();
    private int cargoCount = 0;
    private boolean ballInLaser = false;

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
                IntakeHandler.set(IntakeHandler.State.Forward);
            }
            else {
                IntakeHandler.set(Disabled);
            }
        }
    }

    private void indexConveyorIfCargoInLaserSensor(boolean isCargoInLaser) {
        if (!isIntakeActivated) {
            return;
        }
        if (isCargoInlaser && !ballInLaser) {
            if(cargoCount == 0) {
                ballInLaser = true;
                setCargoCount(1);
                cargoTransfer.forwardIndex();
                ballInLaser = false;
            }
            else if (cargoCount == 1 && !CargoTransferHandler.isIndexing()) {
                ballInLaser = true;
                setCargoCount(2);
            }
        }
    }

    private boolean manualCargoSystem() {
        double manipulatorJoystick = components.manipulatorJoystick.getY();
        if(manipulatorJoystick > Constants.CargoTransfer.ManualCargoSystemControlThreshhold) {
            IntakeHandler.set(IntakeHandler.State.Reverse);
            CargoTransferHandler.set(Constants.CargoTransfer.ReverseIndexMotorSpeed);
        }
        else if (manipulatorJoystick < -Constants.CargoTransfer.ManualCargoSystemControlThreshhold) {
            IntakeHandler.set(IntakeHandler.State.Forward);
            CargoTransferHandler.set(Constants.CargoTransfer.ForwardIndexMotorSpeed);
        }
        else {
            if (isIntakeActivated) {
                intakeHandler.
            }
        }
    }

    public void setCargoCount(int desired) {
        if (cargoCount != desired) {
            cargoCount = desired;
        }
    }
     


}
