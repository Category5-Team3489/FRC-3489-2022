// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IndicatorLeds extends SubsystemBase {
  
  // https://github.com/Category5-Team3489/FRC-3489-2022/blob/main/%20TrunkOrTreatRobot/src/main/java/frc/robot/LedHandler.java

  //Red - Claw empty
  //Green - Claw has object
  //Blue - Climber has rope

  AddressableLED led = new AddressableLED(0);
  AddressableLEDBuffer buffer = new AddressableLEDBuffer(100);

  // led.setData(buffer);

  // buffer.setRGB(i, 197, 21, 232);

  /** Creates a new ExampleSubsystem. */
  public IndicatorLeds() {}

  public void init() {
    led.setLength(buffer.getLength());
    led.setData(buffer);
    led.start();
  }

  public void setLEDRed() {
    for (int i = 0; i < buffer.getLength(); i++) {
        buffer.setRGB(1, 255, 0, 0);
    }
  }

  public void setLEDGreen() {
    for (int i = 0; i < buffer.getLength(); i++) {
        buffer.setRGB(1, 0, 255, 0);
    }
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
