// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  //Joystick joystick;
  
  //WPI_TalonSRX motorTest;
  //WPI_TalonSRX motorTest2;

  private CANSparkMax a = new CANSparkMax(20, MotorType.kBrushless);
  private CANSparkMax b = new CANSparkMax(21, MotorType.kBrushless);

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    //joystick = new Joystick(0);
  

    //motorTest = new WPI_TalonSRX(3);
    //motorTest2 = new WPI_TalonSRX(4);
    a.restoreFactoryDefaults(true);
    b.restoreFactoryDefaults(true);

    b.follow(a);
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

    double time = Timer.getFPGATimestamp();

    //double period = time % 10;

    //a.set(lerp(0, 0.75, period / 10));
    a.set(Math.sin((time * Math.PI)/5d));

    //double speed = joystick.getY();
    //double s = joystick.getX();

    //motorTest.set(speed);
    //motorTest2.set(s);
  }

  private double lerp(double a, double b, double f)
  {
      return a * (1.0 - f) + (b * f);
  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void testInit() {}

  @Override
  public void testPeriodic() {}

  @Override
  public void simulationInit() {}

  @Override
  public void simulationPeriodic() {}
}
