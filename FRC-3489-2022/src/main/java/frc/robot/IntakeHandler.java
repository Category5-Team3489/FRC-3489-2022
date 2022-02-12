package frc.robot;

import frc.robot.framework.RobotHandler;

public class IntakeHandler extends RobotHandler {
    private ComponentsContainer components;
    
    public IntakeHandler(ComponentsContainer components){

    }
    public void intake(){
        components.intakeMotor.set(Constants.intakeSpeed);
    }
    public void output(){
        components.intakeMotor.set(-Constants.intakeSpeed);
    }
    public void stop(){
        components.intakeMotor.stopMotor();
    }
}
