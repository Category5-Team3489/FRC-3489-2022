// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;

public class Robot extends TimedRobot {

  private AutoRunner autoRunner = new AutoRunner();

  @Override
  public void robotInit() {}

  @Override
  public void robotPeriodic() {}

  @Override
  public void autonomousInit() {
    autoRunner.beginExecution(testAuto());
  }

  private AutoInstruction testAuto() {
    AutoInstruction instruction = new DriveInstruction();
    instruction.onFinished(() ->{
      System.out.println();
    });
    return instruction;
  }

  @Override
  public void autonomousPeriodic() {
    autoRunner.periodic();
  }

  @Override
  public void teleopInit() {}

  @Override
  public void teleopPeriodic() {}

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

}
