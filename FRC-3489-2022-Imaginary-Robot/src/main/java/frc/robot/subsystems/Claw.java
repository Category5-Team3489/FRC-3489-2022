// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Claw extends SubsystemBase {
  Solenoid clawLeftSolenoid = new Solenoid(Constants.PcmCanId, PneumaticsModuleType.REVPH, Constants.PcmLeftClawChannelId);
  Solenoid clawRightSolenoid = new Solenoid(Constants.PcmCanId, PneumaticsModuleType.REVPH, Constants.PcmRightClawChannelId);

  /** Creates a new ExampleSubsystem. */
  public Claw() {}

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
