/*-----------------------weeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee                                                                                                                                      ------------    -----------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/* TShirt ver 1  Nathan & Jake  /*
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;


import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SimpleRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.AnalogChannel;
//import edu.wpi.first.wpilibj.DigitalOutput;                                                     
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.DriverStation;

//import edu.wpi.first.wpilibj.GenericHID;
//import edu.wpi.first.wpilibj.Relay;
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SimpleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource                                                                                                                                                                                                                
 * directory.
 */
public class RobotTemplate extends SimpleRobot {
    //Display on driver station
    DriverStationLCD driverLCD = DriverStationLCD.getInstance();
    // Joysticks
    //Joystick leftstick = new Joystick (1);
   // Joystick rightstick = new Joystick (2);
    Joystick gamec = new Joystick(1);
    //UltraSonic
    AnalogChannel safetyL = new AnalogChannel(2);   //Left Ultrasonic in analog 2
    AnalogChannel safetyR = new AnalogChannel(3);   //Right Ultrasonic in analog 3
    public double safeDistanceL = 210;              //voltage returned at 6 feet
    public double safeDistanceR = 210;
    
   
        

    
    //Set compressor - Preasure Switch, Compressor Relay
    ///Compressor comp = new Compressor(1,4);
    Compressor comp = new Compressor(1,2);
    //Relay compR = new Relay(2);
    //Cylinders for fireing
    Solenoid fireA = new Solenoid(1);
    Solenoid fireB = new Solenoid(2);
    Solenoid fireC = new Solenoid(3);
    Solenoid fireD = new Solenoid(4);
    
    //Cyinders to raise and lower
    DoubleSolenoid arm = new DoubleSolenoid(6,7);
    //Motor Controllers (Orintation = Battery in back / motors in front)
    Jaguar mRight = new Jaguar(2); // Right
    Jaguar mLeft = new Jaguar(3); //Left
    RobotDrive chassis = new RobotDrive(mLeft,mRight);
    
    //DigitalOutput mansw = new DigitalOutput(10);
    
    Relay manr = new Relay(6);
    
    public boolean butnone;
//Buttons
        int trig1 =5;      
        int but1 =1; 
        int but2 =2;
        int but3 =3;
        int but4 =4;
        int but8 =8;
        int but9 =9;
    
    public void codeVersion() {
		DriverStation.getInstance().InDisabled(true);
		System.out.println("*******************************************");
		System.out.println("*** T-Shirt Drive Code v9.1 2018                                                 ***");
		System.out.println("*** 2/24/18                                        ***");
		System.out.println("*******************************************");
	}
    public void autonomous() {
        //No autonomous
    }

    /**
     * This function is called once each time the robot enters operator control.
     */
    public void operatorControl() {//teleop
        double speedControl;
        speedControl = 1;
//driverLCD.println(DriverStationLCD.Line.kMain6, 1, "TShirt v1");
        //Put controlls on dashboard
        
        
        //Setup drive
        //Invert may be added to tank drive using Y * -1
        ///chassis.setSafetyEnabled(true);
        chassis.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, true);
        comp.start(); //this turns the compressor on before any launch.
        manr.set(Relay.Value.kOn); //changes positive and negative
        manr.setDirection(Relay.Direction.kForward);
        while (isOperatorControl() &&  isEnabled()) {
            manr.set(Relay.Value.kOff);
            if(gamec.getRawButton(but1)==true){
                fireA.set(true);
            } else if(gamec.getRawButton(but1)==false) {
                fireA.set(false);
                manr.set(Relay.Value.kForward);
            }
          //  if(gamec.getRawButton(trig1)== true){
              //  manr.set(Relay.Value.kForward);
            //}else if(gamec.getRawButton(trig1)==false){
             //  manr.set(Relay.Value.kOff);
            
            
            if(gamec.getRawButton(but2)==true){
                manr.set(Relay.Value.kOff);
                fireB.set(true); 
            }else if(gamec.getRawButton(but2)==false){
                fireB.set(false);
                manr.set(Relay.Value.kForward);
            }
            
            if(gamec.getRawButton(but3)==true){
                manr.set(Relay.Value.kOff);
                fireC.set(true);
            }else if(gamec.getRawButton(3)==false) {
                fireC.set(false);
                manr.set(Relay.Value.kForward);
            }
            
            if(gamec.getRawButton(but4)==true){
                manr.set(Relay.Value.kOff);
                fireD.set(true);
            }else if(gamec.getRawButton(4)==false){
                fireD.set(false);
                manr.set(Relay.Value.kForward);
            }
            
            if(DriverStation.getInstance().getLocation() == 1){
                speedControl = -1.00;
            } else if(DriverStation.getInstance().getLocation() == 2){
                speedControl = -0.85;
            } else if(DriverStation.getInstance().getLocation() == 3){
                speedControl = -0.70;
            }            
            chassis.tankDrive(gamec.getY()*speedControl, gamec.getRawAxis(4)*speedControl);
            //System.out.println(DriverStation.getInstance().getAlliance());//blue 16 red 15
            if(DriverStation.getInstance().getAlliance()== DriverStation.Alliance.kBlue){
                chassis.tankDrive(gamec.getY()*speedControl, gamec.getRawAxis(4)*speedControl);
                System.out.println("Blue");
            }
            if(DriverStation.getInstance().getAlliance()== DriverStation.Alliance.kRed) {
                chassis.tankDrive(gamec.getRawAxis(4)*speedControl, gamec.getY()*speedControl);
                System.out.println("Red");
            }
            
            Timer.delay(0.05);        
       }
     }
   
   
    
    /**
     * This function is called once each time the robot enters test mode.
     */
      
    public void test() {
        //boolean compOnOff = false;
          chassis.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, true);
          comp.start();
          //mansw.set(false);
          manr.set(Relay.Value.kOn);
         manr.setDirection(Relay.Direction.kForward);
         
        while(isEnabled()) {
            if(manr.get() == Relay.Value.kOff) {
                if(gamec.getRawButton(but1)) {  
                        fireA.set(true);
                        System.out.println("FireA");
                }else if(gamec.getRawButton(but2)) {
                        fireB.set(true);
                        System.out.println("FireB");
                }else if(gamec.getRawButton(but3)) {
                        fireC.set(true);
                        System.out.println("FireC");
                }else if(gamec.getRawButton(but4)) {
                        fireD.set(true);
                        System.out.println("FireD");
                } else {
                    System.out.println("Do not fire");
                    fireA.set(false);
                    fireB.set(false);
                    fireC.set(false);
                    fireD.set(false);
                }
            } 
            if(gamec.getRawButton(trig1) == true){
                System.out.println("Forward");
                manr.set(Relay.Value.kOn);
                manr.set(Relay.Value.kForward);
            } else {
                //System.out.println("Off");
                manr.set(Relay.Value.kOff);
            }

                //chassis.tankDrive(gamec.getRawAxis(4),gamec.getY());
          
        Timer.delay(0.05);
        }                
    }
    
    public void disabled() {
        codeVersion();
    }
}
