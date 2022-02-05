package frc.robot;


import frc.robot.framework.RobotHandler;

public class JoystickHandler extends RobotHandler {

    //Get Y values from left drive joystick
    public double getLeftJoystickYValue(){
       return components.leftDriveJoystick.getY();
    }

     //Get Y values from right drive joystick
     public double getRightJoystickYValue(){
        return components.rightDriveJoystick.getY();
     }

     //get button press for autonomous 2

   public boolean getButtonForAuto2(){
      return components.manipulatorJoystick.getRawButtonPressed(10);
   }
}
 