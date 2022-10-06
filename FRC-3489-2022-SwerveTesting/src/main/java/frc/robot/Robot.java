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

  private WPI_TalonFX[] motors = new WPI_TalonFX[8];

  private Joystick joystick = new Joystick(0);

  private PIDController[] steeringControllers = new PIDController[4];

  private double clicksPerRotation = 26204.07;
  // 90 deg * 5 = 32768
  // 16 motor rotations = 90 deg * 5

  private double steeringPosition = 0;

  @Override
  public void robotInit() {
    for (int i = 0; i < 8; i++)
    {
      motors[i] = new WPI_TalonFX(i + 1);
      motors[i].configFactoryDefault();
    }

    for (int i = 0; i < 4; i++)
    {
      steeringControllers[i] = new PIDController(0.0001, 0, 0);
    }
  }

  private WPI_TalonFX getMotor(int pair, boolean isSteeringMotor) {
    int i = 0;
    if (isSteeringMotor)
    {
      i++;
    }
    i += pair * 2;
    return motors[i];
  }

  @Override
  public void robotPeriodic() {
    //double e = steer.getSelectedSensorPosition();
    //System.out.println(e);
  }

  @Override
  public void autonomousInit() {}

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {
    for (int i = 0 ; i < 8; i++)
    {
      motors[i].setSelectedSensorPosition(0);
    }
  }

  @Override
  public void teleopPeriodic() {
    // 50 times per second

    double steering = joystick.getX(); // -1 to 1
    double driveSpeed = joystick.getY(); // -1 to 1

    // 26000 = 1 rotation
    steeringPosition += ((steering * clicksPerRotation) / 50) / 4;

    for (int i = 0 ; i < 4; i++)
    {
      PIDController controller = steeringControllers[i];
      WPI_TalonFX steeringMotor = getMotor(i, true);
      WPI_TalonFX drivingMotor = getMotor(i, false);
      drivingMotor.set(driveSpeed);
      double output = controller.calculate(steeringMotor.getSelectedSensorPosition(), steeringPosition);

      if (output > 1)
      {
        output = 1;
      }
      else if (output < -1)
      {
        output = -1;
      }

      steeringMotor.set(output);
    }

    if (joystick.getRawButton(2))
    {
      for (int i = 0 ; i < 8; i++)
      {
        motors[i].setSelectedSensorPosition(0);
      }
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
