package frc.robot.handlers;

import frc.robot.Constants;
import frc.robot.framework.RobotHandler;

public class IntakeHandler extends RobotHandler {
    
    public void startIntake() {
        components.intakeMotor.set(Constants.IntakeMotorSpeed);
    }

    public void stopIntake() {
        components.intakeMotor.stopMotor();
    }
    
    public void reverseIntake(){
        components.intakeMotor.set(Constants.ReverseIntakeMotorSpeed);
    }

    public boolean isCargoInLaser() {
        // TODO DEBUG
        return components.manipulatorJoystick.getRawButton(Constants.DebugCargoInLaserButton);
        //return components.intakeLaserSensor.get();
    }
    
}
