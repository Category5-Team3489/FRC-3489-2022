package frc.robot;

import frc.robot.framework.RobotHandler;

public class pnematicsHandler extends RobotHandler {
    ComponentsContainer container;

    public void setBottom(boolean botBoolean){
        container.bottomLeftSolenoid.set(botBoolean);
        container.bottomRightSolenoid.set(botBoolean);
    }
    public void setTop(boolean topBoolean){
        container.topLeftSolenoid.set(topBoolean);
        container.topRightSolenoid.set(topBoolean);
    }

    public void setBrake(boolean brakeBoolean){
        container.BrakeSolenoid.set(brakeBoolean);
    }

}
