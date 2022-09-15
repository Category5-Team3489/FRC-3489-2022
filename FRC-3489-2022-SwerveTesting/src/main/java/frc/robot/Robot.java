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

  private PIDController aimController = new PIDController(0.0001, 0, 0);

  //private double aimTargetAngle = 0;

  private double clicksPerRotation = 26204.07;

  @Override
  public void robotInit() {
    aim.configFactoryDefault();
    drive.configFactoryDefault();

    //aimController.setTolerance(1f);

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
    //double a = xbox.getRawAxis(2);
    double b = xbox.getRawAxis(3);
    //System.out.println(a + " : " + b);
    double setpoint = (xbox.getRawAxis(1) + 1) * (clicksPerRotation / 2);
    double o = aimController.calculate(aim.getSelectedSensorPosition(), setpoint);
    if (o > 1)
    {
      o = 1;
    }
    else if (o < -1)
    {
      o = -1;
    }

    aim.set(o);
    drive.set(b / 3);

    if (xbox.getRawButton(4))
    {
      aim.setSelectedSensorPosition(0);
    }
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
