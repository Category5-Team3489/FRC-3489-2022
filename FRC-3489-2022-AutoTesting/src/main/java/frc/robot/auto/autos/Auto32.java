package frc.robot.auto.autos;

import frc.robot.auto.framework.AutoBuilder;

public class Auto32 extends AutoBuilder {

    @Override
    public void build() {

        begin()
        .drive(1000)
        .concurrently(
            print("Started driving 2000 clicks"),
            drive(2000)
        )
        .pause(2)
        .drive(2000);
    }
    
}
