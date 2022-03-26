package frc.robot.handlers;

import frc.robot.Constants;
import frc.robot.framework.RobotHandler;
import frc.robot.interfaces.IShuffleboardState;
import frc.robot.types.IntakeState;

public class IntakeHandler extends RobotHandler implements IShuffleboardState {

    private IntakeState intakeState = IntakeState.Disabled;

    public void stop() {
        if (update(IntakeState.Disabled)) {
            components.intakeMotor.stopMotor();
            components.intakeSolenoid.set(false);
        }
    }

    public void forwardIntake(boolean setSolenoid) {
        if (update(IntakeState.Forward)) {
            components.intakeMotor.set(Constants.ForwardIntakeMotorSpeed);
            if (setSolenoid)
                components.intakeSolenoid.set(true);
        }
    }
    
    public void backwardIntake(boolean setSolenoid){
        if (update(IntakeState.Backward)) {
            components.intakeMotor.set(Constants.BackwardIntakeMotorSpeed);
            if (setSolenoid)
                components.intakeSolenoid.set(true);
        }
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
