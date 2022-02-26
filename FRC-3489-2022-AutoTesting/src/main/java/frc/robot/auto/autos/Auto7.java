package frc.robot.auto.autos;

import frc.robot.Constants;
import frc.robot.auto.framework.AutoBuilder;
import frc.robot.auto.framework.AutoInstruction;

public class Auto7 extends AutoBuilder {

    @Override
    public AutoInstruction build() {
        AutoInstruction first = first();

            first
            .concurrently(      //Shoot
                shoot(0.9, 3),
                pause(1)
                    .cargoTransfer(0.5, 5 * Constants.ClicksPerCargoLength)
            )
            .pause(1)
            .concurrently(      //Intake ball while driving to terminal
                drive(0.65, 13 * Constants.DriveClicksPerFoot),
                intake(0.6, 5)
            );

      return first;
    }   
 } 