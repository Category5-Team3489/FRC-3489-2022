package frc.robot.auto.autos;

import frc.robot.Constants;
import frc.robot.Constants.Auto;
import frc.robot.auto.framework.AutoBuilder;
import frc.robot.auto.framework.AutoInstruction;

public class Auto3 extends AutoBuilder {
    @Override
    public AutoInstruction build() {
        AutoInstruction first = first();

        first
        .concurrently(
            drive(0.5, 4 * Auto.ClicksPerFootDriven), // drive foward 4 ft at 65% speed while intaking
            intake(10)
        )
        .pause(1) // pause 1 second
        .drive(-0.65, 4 * Auto.ClicksPerFootDriven) // drive backward 4 ft at 65% speed
        .pause(1) // pause 1 second
        .concurrently(
            shoot(0.5, 3),
            pause(1)
                .cargoTransfer(0.5, 5 * Constants.CargoTransfer.ClicksPerCargoLength)
        ); // shoot high twice

        return first;
    }
}
