package frc.robot.auto.autos;

import frc.robot.Constants;
import frc.robot.auto.framework.AutoBuilder;
import frc.robot.auto.framework.AutoInstruction;

public class AutoQuick extends AutoBuilder {

    @Override
    public AutoInstruction build() {
        AutoInstruction first = first();

        
        
        first
        .pause(3)
        .concurrently(
            shoot(0.5, 4),
            pause(2)
                .cargoTransfer(0.5, 5 * Constants.CargoTransfer.ClicksPerCargoLength)        
        ) // shoot high once
        .driveSeconds(0.55, 1); // drive out of tarmac was 0.65

        return first;
    }
    
}
