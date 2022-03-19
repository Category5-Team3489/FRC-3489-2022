package frc.robot.handlers;

import frc.robot.Constants;
import frc.robot.framework.RobotHandler;
import frc.robot.interfaces.ISetShuffleboardState;
import frc.robot.types.IntakeState;

public class IntakeHandler extends RobotHandler implements ISetShuffleboardState {

    private IntakeState intakeState = IntakeState.Disabled;

    public void stop() {
        if (update(IntakeState.Disabled))
            components.intakeMotor.stopMotor();
    }

    public void forwardIntake() {
        if (update(IntakeState.Forward))
            components.intakeMotor.set(Constants.ForwardIntakeMotorSpeed);
    }
    
    public void backwardIntake(){
        if (update(IntakeState.Backward))
            components.intakeMotor.set(Constants.BackwardIntakeMotorSpeed);
    }

    public boolean isCargoInLaser() {
        return !components.intakeLaserSensor.get();
    }

    public IntakeState getIntakeState() {
        return intakeState;
    }

    @Override
    public void setShuffleboardState() {
        switch (intakeState) {
            case Disabled:
                shuffleboardHandler.setString(true, "Intake State", "Disabled");
                shuffleboardHandler.setBoolean(true, "Intake Running", false);
                break;
            case Forward:
                shuffleboardHandler.setString(true, "Intake State", "Forward");
                shuffleboardHandler.setBoolean(true, "Intake Running", true);
                break;
            case Backward:
                shuffleboardHandler.setString(true, "Intake State", "High");
                shuffleboardHandler.setBoolean(true, "Intake Running", true);
                break;
        }
    }

    private boolean update(IntakeState desired) {
        if (intakeState != desired) {
            intakeState = desired;
            return true;
        }
        return false;
    }
    
}
