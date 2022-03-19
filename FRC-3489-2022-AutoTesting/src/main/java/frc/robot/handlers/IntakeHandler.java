package frc.robot.handlers;

import frc.robot.Constants;
import frc.robot.framework.RobotHandler;
import frc.robot.types.IntakeState;

public class IntakeHandler extends RobotHandler {

    private IntakeState intakeState = IntakeState.Disabled;

    public void stopIntake() {
        if (update(IntakeState.Disabled))
            components.intakeMotor.stopMotor();
    }

    public void forwardIntake() {
        if (update(IntakeState.Forward))
            components.intakeMotor.set(Constants.IntakeMotorSpeed);
    }
    
    public void backwardIntake(){
        if (update(IntakeState.Backward))
            components.intakeMotor.set(Constants.ReverseIntakeMotorSpeed);
    }

    public boolean isCargoInLaser() {
        return !components.intakeLaserSensor.get();
    }

    public IntakeState getIntakeState() {
        return intakeState;
    }

    private boolean update(IntakeState desired) {
        if (intakeState != desired) {
            intakeState = desired;
            return true;
        }
        return false;
    }
    
}
