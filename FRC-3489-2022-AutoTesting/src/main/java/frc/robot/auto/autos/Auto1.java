package frc.robot.auto.autos;

import frc.robot.Constants;
import frc.robot.auto.framework.AutoBuilder;
import frc.robot.auto.framework.AutoInstruction;

public class Auto1 extends AutoBuilder {

    @Override
    public AutoInstruction build() {
        AutoInstruction first = first();

        first
        .drive(0.65, 12 * Constants.ClicksPerFootDriven) //12 ft at 0.65% 
        .pause(1)
        .turn(0.4, 70)
        .drive(0.65, 8.4375 * Constants.ClicksPerFootDriven); // 8.4375 ft at 0.65% 

        return first;
    }
    
}