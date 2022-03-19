package frc.robot.handlers;

import frc.robot.Constants;
import frc.robot.framework.RobotHandler;
import frc.robot.types.IntakeState;

public class IntakeHandler extends RobotHandler {

    private IntakeState intakeState = IntakeState.Disabled;

    public void stopIntake() {
        components.intakeMotor.stopMotor();
    }

    public void forwardIntake() {
        if (intakeState != IntakeState.Forward)
            components.intakeMotor.set(Constants.IntakeMotorSpeed);
    }
    
    public void backwardIntake(){
        if (intakeState != IntakeState.Backward)
            components.intakeMotor.set(Constants.ReverseIntakeMotorSpeed);
    }

    public boolean isCargoInLaser() {
        //return components.manipulatorJoystick.getRawButton(Constants.ButtonDebugCargoInLaser);
        return !components.intakeLaserSensor.get();
    }

    public IntakeState getIntakeState() {
        return intakeState;
    }
    
}
