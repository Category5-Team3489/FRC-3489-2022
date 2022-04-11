package frc.robot.auto.autos;

import frc.robot.Constants;
import frc.robot.Constants.Auto;
import frc.robot.auto.framework.AutoBuilder;
import frc.robot.auto.framework.AutoInstruction;

public class Auto4 extends AutoBuilder {

    @Override
    public AutoInstruction build() {
        AutoInstruction first = first();

        first
        .concurrently(
            shoot(0.5, 3), // Set shooter to 50% for 3 seconds
            pause(1) // Pause for 1 second to let motor get up to speed
                .cargoTransfer(0.5, 5 * Constants.CargoTransfer.ClicksPerCargoLength) // Move cargo transfer at 50% for 5 ball lengths to shoot ball
        )
        .pause(1) // Wait one second
        .drive(0.65, 12 * Auto.ClicksPerFootDriven) // Drive foward at 65% for 12 feet
        .pause(1) // Wait one second
        .turn(0.4, 120) // turn 120 degres
        .drive(0.65, 3 * Auto.ClicksPerFootDriven); //drive foward 3 feet at 65% speed 
        return first;
    }
    
}
