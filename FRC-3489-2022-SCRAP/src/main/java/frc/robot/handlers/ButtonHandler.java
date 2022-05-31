package frc.robot.handlers;

import frc.robot.Constants;
import frc.robot.framework.RobotHandler;

public class ButtonHandler extends RobotHandler {
    public boolean shouldToggleAutoShoot() {
        return components.manipulatorJoystick.getRawButtonPressed(Constants.Buttons.Manipulator.AutoShoot);
    }
    public boolean shouldToggleSwitchFront() {
        return false;
    }
}
