package frc.robot;

import edu.wpi.first.wpilibj.Joystick;

public class JoystickHandler {
    
    public Joystick leftDriveJoystick;
    public Joystick rightDriveJoystick;
    
    public JoystickHandler(ComponentsContainer components){
        leftDriveJoystick = components.leftDriveJoystick;
        rightDriveJoystick= components.rightDriveJoystick;

    }

    //Get Y values from left drive joystick
    public double getLeftJoystickYValue(){
       return leftDriveJoystick.getY();
    }

     //Get Y values from right drive joystick
     public double getRightJoystickYValue(){
        return rightDriveJoystick.getY();
     }
}
