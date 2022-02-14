package frc.robot;

import frc.robot.framework.RobotHandler;

public class ClimberHandler extends RobotHandler {
    ComponentsContainer container;

    public void setBottom(boolean botBoolean){
        container.bottomLeftSolenoid.set(botBoolean);
        container.bottomRightSolenoid.set(botBoolean);
    }
    public void setTop(boolean topBoolean){
        container.topLeftSolenoid.set(topBoolean);
        container.topRightSolenoid.set(topBoolean);
    }
    public void setHookPnematic(boolean hookBoolean){
        container.hookSolenoid.set(hookBoolean);
    }
    public void setBrake(boolean brakeBoolean){
        container.brakeSolenoid.set(brakeBoolean);
    }
    public void teloscope(double speed, double clicks){

    }
}
