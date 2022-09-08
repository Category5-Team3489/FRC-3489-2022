// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;

public class Robot extends TimedRobot {

  private WPI_TalonFX aim = new WPI_TalonFX(2);
  private WPI_TalonFX drive = new WPI_TalonFX(1);

  private XboxController xbox = new XboxController(0);

  //private PIDController aimController = new PIDController(kp, ki, kd);

  //private double aimTargetAngle = 0;

  @Override
  public void robotInit() {
    aim.configFactoryDefault();
    drive.configFactoryDefault();

    //aimController.setTolerance(1f);

    //aimController.calculate(measurement, setpoint)
  }

  @Override
  public void robotPeriodic() {
    double e = aim.getSelectedSensorPosition();
    System.out.println(e);
  }

  @Override
  public void autonomousInit() {}

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {}

  @Override
  public void teleopPeriodic() {
    double a = xbox.getRawAxis(0);
    double b = xbox.getRawAxis(2);
    aim.set(a);
    drive.set(b);
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
