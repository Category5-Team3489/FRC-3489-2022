// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

    WPI_TalonSRX leftmoter = new WPI_TalonSRX(1);

    WPI_TalonSRX rightmoter = new WPI_TalonSRX(3);
    
    Joystick stick = new Joystick(1); 
    
    AddressableLED lights = new AddressableLED(0);

    AddressableLEDBuffer Blights = new AddressableLEDBuffer(162); 

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {

    lights.setLength(Blights.getLength()); 

    lights.setData(Blights);
    lights.start();

  }

  @Override
  public void robotPeriodic() {}

  @Override
  public void autonomousInit() {}

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {}

  @Override
  public void teleopPeriodic() {

    double X = stick.getX();

    double Y = stick.getY(); 
   
    leftmoter.set(X); 
    
    rightmoter.set(Y); 
    
    for (var i = 0; i < Blights.getLength(); i++) {
      // Sets the specified LED to the RGB values for red
      Blights.setRGB(i, 255, 0, 0);
   }
   
   lights.setData(Blights);

    
  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {
    
    for (var i = 0; i < Blights.getLength(); i++) {
     // Sets the specified LED to the RGB values for red
     Blights.setRGB(i, 0, 0, 0); 
 }

  @Override
  public void testInit() {}

  @Override
  public void testPeriodic() {}

  @Override
  public void simulationInit() {}

  @Override
  public void simulationPeriodic() {}
}
