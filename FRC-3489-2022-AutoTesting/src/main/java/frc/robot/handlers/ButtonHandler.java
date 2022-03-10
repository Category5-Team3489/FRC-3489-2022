package frc.robot.handlers;

import edu.wpi.first.math.filter.Debouncer;
import edu.wpi.first.math.filter.Debouncer.DebounceType;
import frc.robot.Constants;
import frc.robot.framework.RobotHandler;

public class ButtonHandler extends RobotHandler {

    private Debouncer switchFront = new Debouncer(Constants.ButtonDebounceTime, DebounceType.kRising);
    private Debouncer toggleIntake = new Debouncer(Constants.ButtonDebounceTime, DebounceType.kRising);
    private Debouncer shootReleased = new Debouncer(Constants.ButtonDebounceTime, DebounceType.kFalling);

    private boolean switchFrontPressed = false;
    private boolean toggleIntakePressed = false;
    private boolean shootUnpressed = false;

    @Override
    public void teleopPeriodic() {
        //switchFrontPressed = switchFront.calculate(components.rightDriveJoystick.getRawButton(Constants.ButtonSwitchFront));
        toggleIntakePressed = toggleIntake.calculate(components.manipulatorJoystick.getRawButton(Constants.ButtonToggleIntake));
        shootUnpressed = shootReleased.calculate(components.manipulatorJoystick.getRawButton(Constants.ButtonShoot));
    }

    public boolean switchFrontPressed() {
        return components.rightDriveJoystick.getRawButtonPressed(Constants.ButtonSwitchFront) ||
        components.rightDriveJoystick.getRawButtonPressed(Constants.ButtonSwitchFrontB);
    }
    public boolean toggleIntakePressed() {
        return toggleIntakePressed;
    }
    public boolean shootUnpressed() {
        return shootUnpressed;
    }
    
    /*
    private Map<Integer, Debouncer> manipulatorDebouncers = new HashMap<Integer, Debouncer>();

    public Debouncer getManipulatorDebouncer(int button) {
        if (!manipulatorDebouncers.containsKey(button)) {
            manipulatorDebouncers.put(button, new Debouncer());
        }
        return manipulatorDebouncers.get(button);
    }
    */
}
