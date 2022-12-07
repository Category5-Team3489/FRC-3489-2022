// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.Drivetrain;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;

/** An example command that uses an example subsystem. */
public class Teleop extends CommandBase {
  private final Drivetrain drivetrain;
  private final Supplier<Double> leftInput;
  private final Supplier<Double> rightInput;

  public Teleop(Drivetrain drivetrain, Supplier<Double> leftInput, Supplier<Double> rightInput) {
    this.drivetrain = drivetrain;
    this.leftInput = leftInput;
    this.rightInput = rightInput;

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double leftSpeed = leftInput.get();
    double rightSpeed = rightInput.get();

    drivetrain.drive.tankDrive(leftSpeed, rightSpeed);
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
