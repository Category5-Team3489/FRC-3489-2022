package frc.robot.handlers;

import frc.robot.Constants;
import frc.robot.framework.RobotHandler;

public class CargoTransferHandler extends RobotHandler {

    private boolean isIndexing = false;
    private boolean isIndexingForward = true;
    
    public void index() {
        components.cargoTransferMotor.setSelectedSensorPosition(0);
        isIndexing = true;
        isIndexingForward = true;
    }

    public void reverseIndex() {
        components.cargoTransferMotor.setSelectedSensorPosition(0);
        isIndexing = true;
        isIndexingForward = false;
    }

    public void set(double speed) {
        isIndexing = false;
        components.cargoTransferMotor.set(speed);
    }

    public void stopIfNotIndexing() {
        if (!isIndexing) {
            components.cargoTransferMotor.stopMotor();
        }
    }

    public boolean isIndexing() {
        return isIndexing;
    }

    @Override
    public void teleopPeriodic() {
        if (!isIndexing) return;

        double encoderClicks = Math.abs(components.cargoTransferMotor.getSelectedSensorPosition());

        if (encoderClicks < Constants.ClicksPerCargoLength) { // Has not reached target
            if (isIndexingForward) {
                components.cargoTransferMotor.set(Constants.CargoTransferMotorSpeed);
            }
            else {
                components.cargoTransferMotor.set(Constants.ReverseCargoTransferMotorSpeed);
            }
        }
        else { // Has reached target
            set(0);
        }
    }
    
    public void setShootSpeed() {
        set(Constants.CargoTransferShootSpeed);
    }
    
}
