// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class drivesystem extends SubsystemBase {
  /** Creates a new drivesystem. */
  XboxController controller;
  DifferentialDrive drive;
  private CANSparkMax leftFrontDrive;
  private CANSparkMax rightFrontDrive;
  private CANSparkMax leftRearDrive;
  private CANSparkMax rightRearDrive;

  private static final double TurnConstant = 0.5;
  
  public drivesystem() {
    controller = new XboxController(0);
    leftFrontDrive = new CANSparkMax(1, MotorType.kBrushless);
    rightFrontDrive = new CANSparkMax(2, MotorType.kBrushless);
    leftRearDrive = new CANSparkMax(3, MotorType.kBrushless);
    rightRearDrive = new CANSparkMax(4, MotorType.kBrushless);
    drive = new DifferentialDrive(leftFrontDrive, rightFrontDrive);
    leftRearDrive.follow(leftFrontDrive);
    rightRearDrive.follow(rightFrontDrive);
  }

  public void drive() {
    double x = controller.getRightX();
    double y = controller.getRightY();
    double forwardSpeed = y;
    drive.tankDrive(forwardSpeed + (x * TurnConstant), forwardSpeed - (x * TurnConstant));
  }
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    drive();
  }
}
