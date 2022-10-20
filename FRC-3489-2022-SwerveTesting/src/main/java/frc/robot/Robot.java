// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;

public class Robot extends TimedRobot {

  private XboxController xbox = new XboxController(0);

  private SwerveDrive swerveDrive = new SwerveDrive();

  @Override
  public void robotInit() {

  }

  @Override
  public void robotPeriodic() {

  }

  @Override
  public void autonomousInit() {}

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {
    swerveDrive.teleopInit();
  }

  @Override
  public void teleopPeriodic() {
    //double rotation = xbox.getRawAxis(2);  // -1 to 1

    double x = xbox.getRawAxis(0); // -1 to 1
    double y = xbox.getRawAxis(1); // -1 to 1
    double drive = xbox.getRawAxis(3);

    swerveDrive.teleopPeriodic(x, y, drive);
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
  public void simulationPeriodic() {
    double x = xbox.getRawAxis(0); // -1 to 1
    double y = xbox.getRawAxis(1); // -1 to 1

    swerveDrive.teleopPeriodic(x, y, 0);
  }
}
