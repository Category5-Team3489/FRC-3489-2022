package frc.robot.auto.autos;

import frc.robot.Constants.Auto;
import frc.robot.auto.framework.AutoBuilder;
import frc.robot.auto.framework.AutoInstruction;

public class Auto3 extends AutoBuilder {
    @Override
    public AutoInstruction build() {
        AutoInstruction first = first();

        first
        .concurrently(
            drive(0.65, 7.5 * Auto.DriveClicksPerFoot), // 7.5 ft at 65% 
            intake(0.5, 10)
        )
        .drive(-0.65, 7.5 * Auto.DriveClicksPerFoot) // 7.5 ft at 65%
        .concurrently(
            shoot(0.65, 3),
            pause(1)
                .cargoTransfer(0.5, 5 * Auto.CargoTransferClicksPerBall)
        );

        return first;
    }
}