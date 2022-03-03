package frc.robot.handlers;

//import java.util.HashMap;
//import java.util.Map;

import edu.wpi.first.math.filter.Debouncer;
import edu.wpi.first.math.filter.Debouncer.DebounceType;
import frc.robot.Constants;
import frc.robot.framework.RobotHandler;

public class ButtonHandler extends RobotHandler {

    private Debouncer midClimb = new Debouncer(Constants.ButtonDebounceTime, DebounceType.kRising);
    private Debouncer midToHigh = new Debouncer(Constants.ButtonDebounceTime, DebounceType.kRising);
    private Debouncer switchFront = new Debouncer(Constants.ButtonDebounceTime, DebounceType.kRising);
    private Debouncer toggleIntake = new Debouncer(Constants.ButtonDebounceTime, DebounceType.kRising);

    @Override
    public void teleopPeriodic() {
        midClimb.calculate(components.manipulatorJoystick.getRawButton(Constants.ToMidClimber));
        midToHigh.calculate(components.manipulatorJoystick.getRawButton(Constants.MidToHighClimber));
        switchFront.calculate(components.rightDriveJoystick.getRawButton(Constants.SwitchFront));
        toggleIntake.calculate(components.manipulatorJoystick.getRawButton(Constants.ToggleIntakeButton));
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
