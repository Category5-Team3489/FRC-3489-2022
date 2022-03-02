package frc.robot.auto.autos;

import frc.robot.Constants;
import frc.robot.auto.framework.AutoBuilder;
import frc.robot.auto.framework.AutoInstruction;

public class Auto5 extends AutoBuilder {
   
    @Override
    public AutoInstruction build() {
        AutoInstruction first = first();

        first
        .concurrently(
            shoot(0.5, 3), // Set shooter to 50% for 3 seconds
            pause(1) // Pause for 1 second before shooting ball
                .cargoTransfer(0.5, 5 * Constants.ClicksPerCargoLength) // Move cargo transfer at 50% for 5 ball lengths to shoot ball
        )
        .concurrently(
            drive(0.65, 7.5 * Constants.DriveClicksPerFoot), //drive foward at 65% and for 7.5 feet 
            intake(0.8, 2) //intake for 80 and 2 
        ) 
        .turn(0.4, 180) //turn backwards 
        .drive(0.65, 7.5 * Constants.DriveClicksPerFoot)//drives for 7.5 foot 
        .turn(0.4, 30) // Turn at 40% speed 30 degrees to the left
        .drive(0.65, 21 * Constants.DriveClicksPerFoot);//drives for 21foot 

        return first; 
    }

}



