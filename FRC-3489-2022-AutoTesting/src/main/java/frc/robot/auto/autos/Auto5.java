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
            drive(0.65, 7.5 * Constants.DriveClicksPerFoot), //drive foward at 65% and for 7.5 feet 
            intake(2) //intake for and 2 
        )
        .pause(1)
        .concurrently(
            shoot(0.5, 3), // Set shooter to 50% for 3 seconds
            pause(1) // Pause for 1 second before shooting ball
                .cargoTransfer(0.5, 5 * Constants.ClicksPerCargoLength) // Move cargo transfer at 50% for 5 ball lengths to shoot ball
        )
        .pause(1)
        .turn(0.4, 180) //turn backwards 
        .pause(1)
        .drive(0.65, 7.5 * Constants.DriveClicksPerFoot)//drives for 7.5 foot 
        .pause(1)
        .turn(0.4, 30) // Turn at 40% speed 30 degrees to the left
        .pause(1)
        .drive(0.65, 21 * Constants.DriveClicksPerFoot);//drives for 21foot 

        return first; 
    }

}



