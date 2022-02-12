package frc.robot.auto.autos;

import frc.robot.Constants.Auto;
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
                    .cargoTransfer(0.5, 5 * Auto.CargoTransferClicksPerBall)
            )
            .pause(1)
            .concurrently(      //Drive forward, intake ball, and drive to terminal
                drive(0.65, 13 * Auto.DriveClicksPerFoot),
                intake(0.6, 5)
            );

      return first;
    }   
 } 