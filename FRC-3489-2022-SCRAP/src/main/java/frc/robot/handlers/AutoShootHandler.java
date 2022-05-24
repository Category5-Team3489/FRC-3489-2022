package frc.robot.handlers;

import frc.robot.framework.RobotHandler;

public class AutoShootHandler extends RobotHandler {
    public enum State {
        Driving,
        Aiming,
        Accelerating,
        Shooting,
    }

    // State
    private State currentState = State.Driving;

    public boolean setState(State state) {
        if (currentState != state) {
            currentState = state;
            return true;
        }
        return false;
    }
    public boolean isAutoShooting() {
        return currentState != State.Driving;
    }

    // Handling shared control of the drive train
    public void gaveControl() {

    }
    public void tookControl() {

    }
}
