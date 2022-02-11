package frc.robot.auto.autos;

import frc.robot.Constants.Auto;
import frc.robot.auto.framework.AutoBuilder;
import frc.robot.auto.framework.AutoInstruction;

public class Auto2 extends AutoBuilder {
    @Override
    public AutoInstruction build() {
        AutoInstruction first = first();  

        first
        .concurrently(
            shoot(0.65, 3),
            pause(1)
                .cargoTransfer(0.5, 5 * Auto.CargoTransferClicksPerBall)        
        )
        .pause(1)
        .drive(0.65, 8 * Auto.DriveClicksPerFoot)
        .pause(1)
        .drive(-0.65, 8 * Auto.DriveClicksPerFoot)
        .concurrently(
            shoot(0.65, 3),
            pause(1)
                .cargoTransfer(0.5, Auto.CargoTransferClicksPerBall)
        );

        return first;
    }
}