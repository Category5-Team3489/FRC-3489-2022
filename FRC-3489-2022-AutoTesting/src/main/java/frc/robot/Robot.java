// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.framework.RobotManager;
import frc.robot.utils.CSVUtils;

public class Robot extends TimedRobot {

  private RobotManager robotManager;

  // TODO Max rate of change of set speed for teleop driving
  // TODO Button debouncer utils
  // TODO Look into feedforward
  // TODO Look into PID
  // TODO Rev robotics library and code
  // TODO Document auto framework
  // TODO Shuffleboard utils
  // TODO Simplify auto framework
  // TODO Logger handler
  // TODO High frequency periodic method in auto for PID, 200hz maybe
  // TODO Get class name method in RobotReferences? for logger

  // PID
  // TODO look at Ziegler–Nichols tuning
  // TODO Grapher for PID stuff https://app.rawgraphs.io/, include T, P, I, D, Error
  // TODO Scale PID constants to within resonable magnitudes
  // TODO Integrator anti-windup?
  // TODO Conditional integration? May be better than clamping??
  // TODO Clamp integration when D is above threshold? May cause jerk when I starts influencing

  // PID Constant interpolation?
  // PID X set speed, Y voltage drop
  // TODO Revaluate PID constants when accel stops?

  // TODO Fix 2 drive docs
  // TODO Auto docs

  // How to connect to roborio with ftp
  // https://docs.wpilib.org/en/stable/docs/software/roborio-info/roborio-ftp.html

  // Current
  // TODO Get rawgraphs locally https://github.com/rawgraphs/rawgraphs-app


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
    System.out.println(robotManager.components.leftFrontDriveMotor.getSelectedSensorPosition());
  }

  @Override
  public void disabledInit() {
    robotManager.disabledInit();
    CSVUtils.write("test.csv", true);
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
