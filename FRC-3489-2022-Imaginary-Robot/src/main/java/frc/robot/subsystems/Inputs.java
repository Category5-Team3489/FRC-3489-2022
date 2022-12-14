// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.function.Supplier;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardContainer;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Inputs extends SubsystemBase {
  //Declarations
  //Joysticks
  public Joystick leftDriveJoystick = new Joystick(Constants.leftDriveJoystickId);
  public Joystick rightDriveJoystick = new Joystick(Constants.rightDriveJoystickId);
  public Joystick manipulatorJoystick = new Joystick(Constants.manipulatorJoystickId);

  //Shuffleboard
  private ShuffleboardTab tab = Shuffleboard.getTab("Drive");
  
  public Inputs() {
  }

  public Supplier<Double> getLeftDriveInput() {
    return () -> leftDriveJoystick.getY();
  }

  public Supplier<Double> getRightDriveInput() {
    return () -> rightDriveJoystick.getY();
  }




  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
