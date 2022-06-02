package frc.robot.handlers;

import frc.robot.Constants;
import frc.robot.framework.RobotHandler;

public class IntakeHandler extends RobotHandler {
    public enum State {
        Disabled,
        Reverse,
        Forward
    }

    private State currentState = State.Disabled;
    private boolean currentSolenoidState = false;

    public void set(State state) {
        if (update(state)) {
            switch (currentState) {
                case Disabled:
                    components.intakeMotor.stopMotor();
                    break;
                case Reverse:
                    components.intakeMotor.set(Constants.Intake.ReverseMotorSpeed);
                    break;
                case Forward:
                    components.intakeMotor.set(Constants.Intake.ForwardMotorSpeed);
                    break;
            }
        }
    }

    public void setReverse(boolean setSolenoid) {
        set(State.Reverse);
        setSolenoid(setSolenoid);
    }
    public void setForward(boolean setSolenoid) {
        set(State.Forward);
        setSolenoid(setSolenoid);
    }
    public void setStopped() {
        set(State.Disabled);
        setSolenoid(false);
    }

    private void setSolenoid(boolean state) {
        if (currentSolenoidState != state) {
            currentSolenoidState = state;
            components.intakeSolenoid.set(state);
        }
    }

    public boolean isCargoInLaserSensor() {
        return !components.intakeLaserSensor.get();
    }

    private boolean update(State state) {
        if (currentState != state) {
            currentState = state;
            initState();
            return true;
        }
        return false;
    }
    private void initState() {

    }
}