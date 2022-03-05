package frc.robot.auto.autos;

import frc.robot.Constants;
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
                .cargoTransfer(0.5, 5 * Constants.ClicksPerCargoLength)        
        )
        .pause(1)
        .concurrently(
            intake(5),
            drive(0.65, 4 * Constants.ClicksPerFootDriven)
        )
        .pause(1)
        .drive(-0.65, 4 * Constants.ClicksPerFootDriven)
        .concurrently(
            shoot(0.65, 3),
            pause(1)
                .cargoTransfer(0.5, Constants.ClicksPerCargoLength)
        
        );

        return first;
    }
}