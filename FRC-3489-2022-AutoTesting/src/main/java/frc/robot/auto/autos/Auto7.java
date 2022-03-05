package frc.robot.auto.autos;

import frc.robot.Constants;
import frc.robot.auto.framework.AutoBuilder;
import frc.robot.auto.framework.AutoInstruction;
// shoot, intake, drive to terminal
public class Auto7 extends AutoBuilder {

    @Override
    public AutoInstruction build() {
        AutoInstruction first = first();

            first
            .concurrently(      //Shoot High
                shoot(0.5, 3),
                pause(1)
                    .cargoTransfer(0.5, 5 * Constants.ClicksPerCargoLength)
            )
            .pause(1)
            .concurrently(      //Intake ball while driving to terminal
                drive(0.65, 4 * Constants.ClicksPerFootDriven),
                intake(5)
            )
            .pause(1)
            .turn(0.5, -30)    //Turn right 30 degrees
            .pause(1)
            .drive(0.5, 12 * Constants.ClicksPerFootDriven);   // drive forward 12 ft

      return first;
    }   
 } 