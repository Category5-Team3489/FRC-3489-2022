package frc.robot.handlers;

import frc.robot.Constants;
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
    public void telescope(Double teleSpeed){
        container.climbMotor.set(teleSpeed);
    }
    public void midToHi(){
        
        setTop(true); //extend top pnematics
        
        setBrake(true); //de-activate brake
        
        setBrake(false); //activate brake
        setHookPnematic(true); //energize hook
        setTop(false); //retract top pnematics
        setBottom(false); //retract bottom pnematics
        setHookPnematic(false); //de-energize hook
    }
    public void lowtoMid(){
        //drive backward between the mid and high bar for manual 
        setBrake(false); //activate brake
        setBottom(true); //extend bottom pnematics 
        telescope(1.0); //extends telescope at a speed of 1 
        //drive foward to make contact with the mid bar 
        telescope(-1.0);  //retract telescope number at a speed of 1 

    }
    public void climberButtons(){
        boolean midClimbButtonPressed = container.manipulatorJoystick.getRawButtonPressed(Constants.ToMidClimber);
        boolean midToHighButtonPressed = container.manipulatorJoystick.getRawButtonPressed(Constants.MidToHighClimber);
        boolean activateClimberButtonPressed = container.manipulatorJoystick.getRawButtonPressed(Constants.ActivateTheClimber);
        boolean midClimb = false;
        boolean midToHighClimb = false;

        if (midClimbButtonPressed){
            midClimb = !midClimb;
        }
        if(midToHighButtonPressed){
            midToHighClimb = !midToHighClimb;
        }
        if(midClimb){
            if (activateClimberButtonPressed) {
                lowtoMid();
            }
        }
        if (midToHighClimb) {
            if (activateClimberButtonPressed) {
                midToHi();
            } 
                
        } 
    }
}
