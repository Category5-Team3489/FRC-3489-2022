package frc.robot.handlers;

import frc.robot.auto.framework.AutoBuilder;
import frc.robot.containers.ComponentsContainer;
import frc.robot.framework.RobotHandler;

public class ClimberHandler extends RobotHandler{
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
    public void telescope(int teleInt){
        container.climbMotor.set(teleInt);
    }
    public void midToHi(){
        AutoBuilder.pause(1); //pause 1 second
        setTop(true); //extend pnematic 3
        AutoBuilder.pause(1);//pause 1 second
        setBrake(true); //energize pnematic 1
        AutoBuilder.pause(1); //pause __ seconds to lower two inches
        setBrake(false);
        AutoBuilder.pause(1); //pause 1 second
        setHookPnematic(false); //de-energize pnematic 4
        AutoBuilder.pause(1); //pause 1 second
        setTop(false); //de-energize pnematic 3
        setBottom(false); //de-energize pnematic 2
    }
    public void lowtoMid(){
        //drive backward between the mid and high bar 
        setBrake(false); //energize pnematic 1 
        AutoBuilder.pause(1); // pauses 1 second 
        setBottom(true); //extend pnematic 2 
        //drive foward to make contact with the mid bar 
        AutoBuilder.pause(1);
        //retract telescope number 1 

    }
}
