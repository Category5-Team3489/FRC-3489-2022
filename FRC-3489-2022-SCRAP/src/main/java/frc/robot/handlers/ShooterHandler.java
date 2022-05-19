package frc.robot.handlers;

import java.util.function.Consumer;

import com.ctre.phoenix.motorcontrol.ControlMode;

import frc.robot.framework.RobotHandler;

public class ShooterHandler extends RobotHandler {
    enum State {
        Disabled,
        Percent,
        PID
    }

    private State currentState = State.Disabled;
    private double percentSpeedBottom = 0;
    private double percentSpeedTop = 0;

    public void setShooterPercent(double bottom, double top) {
        if (shouldUpdate(State.Percent) || shouldUpdatePercent(bottom, top)) {
            components.bottomShooterMotor.set(bottom);
            components.topShooterMotor.set(top);
        }
    }

    public void shootLow() {
        setShooterPercent(Constants.Bottom, top);
        // dont update
    }

    private boolean shouldUpdate(State state) {
        if (currentState != state) {
            currentState = state;
            return true;
        }
        return false;
    }

    private boolean shouldUpdatePercent(double bottom, double top) {
        if (percentSpeedBottom != bottom || percentSpeedTop != top) {
            percentSpeedBottom = bottom;
            percentSpeedTop = top;
            return true;
        }
        return false;
    }



}
