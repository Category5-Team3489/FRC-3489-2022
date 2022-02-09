package frc.robot.auto.autos;

import frc.robot.Constants.Auto;
import frc.robot.auto.framework.AutoBuilder;
import frc.robot.auto.framework.AutoInstruction;

public class Auto4 extends AutoBuilder {

    @Override
    public AutoInstruction build() {
        AutoInstruction first = first();

        first
        .concurrently(
            shoot(0.9, 3), // Set shooter to 90% for 3 seconds
            pause(1) // Pause for 1 second before shooting ball
                .cargoTransfer(0.5, 5 * Auto.CargoTransferClicksPerBall) // Move cargo transfer at 50% for 5 ball lengths to shoot ball
        )
        .pause(1) // Wait one sec after shooting
        .drive(0.65, 8 * Auto.DriveClicksPerFoot) // Drive at 65% for 8 feet
        .pause(1) // Wait one sec for ball to roll away
        .turn(0.4, 30) // Turn at 40% speed 30 degrees to the left
        .drive(0.65, 6 * Auto.DriveClicksPerFoot); // Drive at 65% for 6 feet
        
        return first;
    }
    
}
