// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.framework.RobotManager;

public class Robot extends TimedRobot {

  private RobotManager robotManager;

  @Override
  public void robotInit() {
    System.out.println("4/20/22 v1");
    robotManager = new RobotManager(this);
    robotManager.robotInit();
    addPeriodic(() -> robotManager.robotFastPeriodic(), Constants.FastPeriodicPeriod);
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
