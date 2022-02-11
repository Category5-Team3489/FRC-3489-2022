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
            .concurrently(      //Drive forward and intake
                drive(0.65, 6 * Auto.DriveClicksPerFoot),
                intake(0.6, 10)
            )
            .pause(1)
            .drive(-0.65, -6 * Auto.DriveClicksPerFoot)//Drive Back
            .turn(0.65, -90)       //Turn right
            .drive(0.65, 5 * Auto.DriveClicksPerFoot);  //Drive forward

      return first;
    }   
 } 