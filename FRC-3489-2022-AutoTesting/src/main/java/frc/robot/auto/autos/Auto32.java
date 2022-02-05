package frc.robot.auto.autos;

import frc.robot.auto.framework.AutoBuilder;

public class Auto32 extends AutoBuilder {

    @Override
    public void build() {
        drive(2000).onCompleted(load(
            pause(3).onCompleted(load(
                drive(1000).onCompleted(load(
                    print("Completed Auto 32!").onCompleted(load(
                        print("Finished printing")
                    ))
                ))
            ))
        ));
    }
    
}
