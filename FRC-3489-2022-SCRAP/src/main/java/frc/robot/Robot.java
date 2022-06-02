// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.framework.RobotManager;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  private RobotManager manager;

  @Override
  public void robotInit() {
    manager = new RobotManager(this);
    manager.robotInit();
    addPeriodic(() -> manager.robotFastPeriodic(), 1.0 / 200.0);
  }

  @Override
  public void robotPeriodic() {
    manager.robotPeriodic();
  }

  @Override
  public void autonomousInit() {
    manager.autonomousInit();
  }

  @Override
  public void autonomousPeriodic() {
    manager.autonomousPeriodic();
  }

  @Override
  public void teleopInit() {
    manager.teleopInit();
  }

  @Override
  public void teleopPeriodic() {
    manager.teleopPeriodic();
  }

  @Override
  public void disabledInit() {
    manager.disabledInit();
  }

  @Override
  public void disabledPeriodic() {
    manager.disabledPeriodic();
  }

  @Override
  public void testInit() {
    manager.testInit();
  }

  @Override
  public void testPeriodic() {
    manager.testPeriodic();
  }

  @Override
  public void simulationInit() {
    manager.simulationInit();
  }

  @Override
  public void simulationPeriodic() {
    manager.simulationPeriodic();
  }
}
