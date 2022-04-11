package frc.robot.auto.autos;

import frc.robot.Constants;
import frc.robot.Constants.Auto;
import frc.robot.auto.framework.AutoBuilder;
import frc.robot.auto.framework.AutoInstruction;

public class Auto2 extends AutoBuilder {
    @Override
    public AutoInstruction build() {
        AutoInstruction first = first();  

        first
        .concurrently(
            shoot(0.5, 3),
            pause(1)
                .cargoTransfer(0.5, 5 * Constants.CargoTransfer.ClicksPerCargoLength)        
        ) // shoot high once
        .pause(1)
        .concurrently(
            intake(5),
            drive(0.65, 4 * Auto.ClicksPerFootDriven)
        ) // intake and drive foward 4 feet
        .pause(1) 
        .drive(-0.65, 4 * Auto.ClicksPerFootDriven) // drive backward 4 feet
        .concurrently(
            shoot(0.5, 3),
            pause(1)
                .cargoTransfer(0.5, Constants.CargoTransfer.ClicksPerCargoLength)
        // shoot high once
        );

        return first;
    }
}