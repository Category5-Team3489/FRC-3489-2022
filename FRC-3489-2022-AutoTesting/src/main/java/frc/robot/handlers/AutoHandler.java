package frc.robot.handlers;

import java.util.HashMap;
import java.util.Map;

import frc.robot.auto.autos.*;
import frc.robot.auto.framework.AutoBuilder;
import frc.robot.auto.framework.AutoRunner;
import frc.robot.framework.RobotHandler;
import frc.robot.interfaces.IShuffleboardState;

public class AutoHandler extends RobotHandler implements IShuffleboardState {

    public AutoRunner runner;

    private Map<Integer, AutoBuilder> autos = new HashMap<Integer, AutoBuilder>();

    private int selectedAuto = 0;

    public AutoHandler() {
        autos.put(1, new Auto1());
        autos.put(2, new Auto2());
        autos.put(3, new Auto3());
        autos.put(4, new Auto4());
        autos.put(5, new Auto5());
        autos.put(6, new Auto6());
        autos.put(7, new Auto7());
        autos.put(16, new AutoQuick());
        autos.put(17, new AutoQuickPickup());
        autos.put(18, new AutoQuickPickupAim());
        autos.put(32, new Auto32());
        autos.put(42, new AutoNew());
        autos.put(43, new AutoNewBlue());
        autos.put(44, new AutoNewRed());
    }

    @Override
    public void robotInit() {
        shuffleboardHandler.createAutoChooserWidget();
        runner = new AutoRunner(robotManager);
    }

    @Override
    public void robotPeriodic() {
        runner.periodic();
        int currentSelectedAuto = shuffleboardHandler.getSelectedAuto();
        if (selectedAuto != currentSelectedAuto) {
            selectedAuto = currentSelectedAuto;
            setShuffleboardState();
        }
    }

    @Override
    public void robotFastPeriodic() {
        runner.fastPeriodic();
    }

    @Override
    public void autonomousInit() {
        runner.cancelExecution();
        int selectedAuto = shuffleboardHandler.getSelectedAuto();
        if (!autos.containsKey(selectedAuto))
            return;
        AutoBuilder auto = autos.get(selectedAuto);
        robotManager.copyReferences(auto);
        auto.setRunner(runner);
        auto.begin(auto.build());
    }

    @Override
    public void teleopInit() {
        runner.cancelExecution();
    }

    @Override
    public void disabledInit() {
        runner.cancelExecution();
    }

    @Override
    public void setShuffleboardState() {
        shuffleboardHandler.setNumber(true, "Selected Auto", selectedAuto);
    }
}
