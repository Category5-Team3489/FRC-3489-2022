package frc.robot.handlers;

import frc.robot.Constants;
import frc.robot.containers.ComponentsContainer;
import frc.robot.framework.RobotHandler;

public class ClimberHandler extends RobotHandler{
    ComponentsContainer container;


    public void setLower(boolean value){
        container.lowerLeftSolenoid.set(value);
        container.lowerRightSolenoid.set(value);
    }
    public void setUpper(boolean value){
        container.upperLeftSolenoid.set(value);
        container.upperRightSolenoid.set(value);
    }
    public void setHooks(boolean value){
        container.leftHookSolenoid.set(value);
        container.rightHookSolenoid.set(value);
    }
    public void setBrake(boolean value){
        container.brakeSolenoid.set(value);
    }
    public void telescope(Double speed){
        container.telescopeMotor.set(speed);
    }
    public void midToHi(){
        
        setUpper(true); //extend top pnematics
        
        setBrake(true); //de-activate brake
        
        setBrake(false); //activate brake
        setHooks(true); //energize hook
        setUpper(false); //retract top pnematics
        setLower(false); //retract bottom pnematics
        setHooks(false); //de-energize hook
    }
    public void lowtoMid(){
        //drive backward between the mid and high bar for manual 
        setBrake(false); //activate brake
        setLower(true); //extend bottom pnematics 
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

    // Turn off all other stuff, intake, cargo mover, shooter

    private void disableOtherSystems() {

    }

    private void init() {
        setBrake(false);
        setLower(false);
        setUpper(false);
        setHooks(false);
    }

    private void 
}
