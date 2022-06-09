package frc.robot.handlers;

import frc.robot.Constants;
import frc.robot.framework.RobotHandler;

public class CargoTransferHandler extends RobotHandler {
    public enum IndexingState {
        Disabled,
        Reverse,
        Forward
    }

    private IndexingState currentState = IndexingState.Disabled;
    private double currentSpeed = 0;

    public void setIndexing(IndexingState state) {
        if (update(state)) {
            // TODO Shuffleboard widgets
        }
    }

    public void setReverse() {
        components.cargoTransferMotor.setSelectedSensorPosition(0);
        setIndexing(IndexingState.Reverse);
    }

    public void setForward() {
        components.cargoTransferMotor.setSelectedSensorPosition(0);
        setIndexing(IndexingState.Forward);
    }

    public void set(double speed) {
        if (update(IndexingState.Disabled) || currentSpeed != speed) {
            // TODO Shuffleboard stuff
            currentSpeed = speed;
            if (speed == 0) {
                components.cargoTransferMotor.stopMotor();
            }
            else {
                components.cargoTransferMotor.set(speed);
            }
        }
    }

    public void stop() {
        set(0);
    }

    public void stopIfNotIndexing() {
        if (!isIndexing()) {
            stop();
        }
    }

    public boolean isIndexing() {
        return currentState != IndexingState.Disabled;
    }

    @Override
    public void teleopPeriodic() {
        if (!isIndexing()) {
            return;
        }

        double encoderClicks = Math.abs(components.cargoTransferMotor.getSelectedSensorPosition());

        if (encoderClicks < Constants.CargoTransfer.ClicksPerCargoLength) { // Has not reached target
            if (currentState == IndexingState.Reverse) {
                components.cargoTransferMotor.set(Constants.CargoTransfer.ReverseIndexMotorSpeed);
            }
            else {
                components.cargoTransferMotor.set(Constants.CargoTransfer.ForwardIndexMotorSpeed);
            }
        }
        else { // Has reached target
            stop();
        }
    }

    public void setShootSpeed() {
        components.cargoTransferMotor.set(Constants.CargoTransfer.ShootMotorSpeed);
    }

    private boolean update(IndexingState state) {
        if (currentState != state) {
            currentState = state;
            return true;
        }
        return false;
    }
}