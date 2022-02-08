// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.framework.RobotManager;

public class Robot extends TimedRobot {

  private RobotManager robotManager;

  // TODO Max rate of change of set speed for teleop driving
  // TODO Button debouncer utils
  // TODO DifferentialDrive.setRightSideInverted(boolean);
  // TODO Look into feedforward
  // TODO Need to turn all safeties off?
  // TODO Test auto framework
  // TODO Look into PID
  // TODO Rev robotics library and code
  // TODO Document auto framework
  // TODO Shuffleboard utils
  // TODO Simplify auto framework

  // Current
  // TODO ConcurrentInstruction next?
  // TODO Fix 2 drive docs
  // TODO Auto docs

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

}
