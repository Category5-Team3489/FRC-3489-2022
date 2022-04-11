package frc.robot.auto.autos;

import frc.robot.Constants.Auto;
import frc.robot.auto.framework.AutoBuilder;
import frc.robot.auto.framework.AutoInstruction;

public class Auto1 extends AutoBuilder {

    @Override
    public AutoInstruction build() {
        AutoInstruction first = first();

        first
        .drive(0.65, 12 * Auto.ClicksPerFootDriven) //12 ft at 0.65% 
        .pause(1) // pause 1 scond
        .turn(0.4, 70) // turn 70 degrees
        .drive(0.65, 8.4375 * Auto.ClicksPerFootDriven); // 8.4375 ft at 0.65% 

        return first;
    }
    
}