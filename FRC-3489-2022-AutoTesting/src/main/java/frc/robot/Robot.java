// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.framework.RobotManager;

public class Robot extends TimedRobot {

  private RobotManager robotManager;

  // PRIORITY
  // TODO Climber

  // LATER
  // TODO Logger handler
  // TODO Button debouncer utils
  // TODO Shooter velocity control with PID
  // TODO Improve slew rate limiting for drive code, may only want to use abrupt breaking
  // TODO Fix 2 drive docs

  // MAYBE???
  // TODO Auto docs
  // TODO Get class name method in RobotReferences? for logger

  // Useful info
  // look at Ziegler–Nichols tuning
  // https://docs.wpilib.org/en/stable/docs/software/roborio-info/roborio-ftp.html
  // https://github.com/rawgraphs/rawgraphs-app

  // OLD
  // TODO Conditional integration? May be better than clamping??
  // TODO Clamp integration when D is above threshold? May cause jerk when I starts influencing
  // TODO Integrator anti-windup?
  // PID Constant interpolation?
  // PID X set speed, Y voltage drop
  // TODO Revaluate PID constants when accel stops?

  @Override
  public void robotInit() {
    robotManager = new RobotManager(this);
    robotManager.robotInit();
  }

  @Override
  public void robotPeriodic() {
    robotManager.robotPeriodic();
  }

  @Override
  public void autonomousInit() {
    robotManager.autonomousInit();
  }

  @Override
  public void autonomousPeriodic() {
    robotManager.autonomousPeriodic();
  }

  @Override
  public void teleopInit() {
    robotManager.teleopInit();
  }

  @Override
  public void teleopPeriodic() {
    robotManager.teleopPeriodic();
  }

  @Override
  public void disabledInit() {
    robotManager.disabledInit();
  }

  @Override
  public void disabledPeriodic() {
    robotManager.disabledPeriodic();
  }

  @Override
  public void testInit() {
    robotManager.testInit();
  }

  @Override
  public void testPeriodic() {
    robotManager.testPeriodic();
  }

  @Override
  public void simulationInit() {

  }

  @Override
  public void simulationPeriodic() {
    
  }

}
