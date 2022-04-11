package frc.robot.auto.autos;

import frc.robot.Constants;
import frc.robot.Constants.Auto;
import frc.robot.auto.framework.AutoBuilder;
import frc.robot.auto.framework.AutoInstruction;

public class Auto5 extends AutoBuilder {
   
    @Override
    public AutoInstruction build() {
        AutoInstruction first = first();

        first
        
        .concurrently(
            drive(0.65, 4 * Auto.ClicksPerFootDriven), //drive foward at 65% and for 4 feet 
            intake(2) //intake for 2 seconds
        )
        .pause(1)
        .drive(-0.65, 4 * Auto.ClicksPerFootDriven)//drives backward for 4 feet 
        .concurrently(
            shoot(0.5, 3), // Set shooter to 50% for 3 seconds
            pause(1) // Pause for 1 second for motor to get to speed
                .cargoTransfer(0.5, 5 * Constants.CargoTransfer.ClicksPerCargoLength) // Move cargo transfer at 50% for 5 ball lengths to shoot ball
        )
        .pause(1)
        .turn(0.4, 90) // Turn at 40% speed 90 degrees to the left
        .pause(1)
        .drive(0.65, 4 * Auto.ClicksPerFootDriven);//drives foward for 4 feet 

        return first; 
    }

}



