package frc.robot.auto.autos;

import frc.robot.Constants;
import frc.robot.auto.framework.AutoBuilder;
import frc.robot.auto.framework.AutoInstruction;

public class AutoQuick extends AutoBuilder {

    @Override
    public AutoInstruction build() {
        AutoInstruction first = first();

        // shoot
        // drive out of tarmac
        first
        .pause(3)
        .concurrently(
            shoot(0.5, 4),
            pause(2)
                .cargoTransfer(0.5, 5 * Constants.ClicksPerCargoLength)        
        )
        .drive(0.65, 6 * Constants.ClicksPerFootDriven);

        return first;
    }
    
}
