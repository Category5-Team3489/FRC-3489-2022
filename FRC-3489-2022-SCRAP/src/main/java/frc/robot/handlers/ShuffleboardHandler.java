package frc.robot.handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;
import frc.robot.framework.RobotHandler;

public class ShuffleboardHandler extends RobotHandler {
    public class Doodad {
        private SimpleWidget widget;

        private Object cachedValue = null;

        private boolean timedUpdate = false;
        private Timer timer = new Timer();
        private double updatePeriod = 1;
        private double carryover = 0;

        public Doodad(Tab tab, String title, Object defaultValue, Consumer<SimpleWidget> init) {
            timer.start();
            cachedValue = defaultValue;
            widget = tab.get().add(title, defaultValue);
            init.accept(widget);
        }

        public Doodad withTimedUpdate(double frequency) {
            timedUpdate = true;
            updatePeriod = 1 / frequency;
            return this;
        }

        public void set(Object value) {
            if (timedUpdate) {
                cachedValue = value;
            }
            else {
                widget.getEntry().setValue(cachedValue);
            }
        }

        public void pollUpdate() {
            if (!timedUpdate) {
                return;
            }
            if (timer.hasElapsed(updatePeriod - carryover)) {
                carryover = timer.get() - updatePeriod;
                timer.reset();
                widget.getEntry().setValue(cachedValue);
            }
        }
    }

    public enum Tab {

        Main("Main"),
        Auto("Auto"),
        Diagnostic("Diagnostic");

        private String title = "";

        private Tab(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }

        public ShuffleboardTab get() {
            return Shuffleboard.getTab(getTitle());
        }
    }

    private List<Doodad> doodads = new ArrayList<Doodad>();

    @Override
    public void robotPeriodic() {
        doodads.forEach(Doodad::pollUpdate);
    }

    public Doodad newDoodad(Tab tab, String title, Object defaultValue, Consumer<SimpleWidget> init) {
        Doodad doodad = newDoodad(tab, title, defaultValue, init);
        doodads.add(doodad);
        return doodad;
    }
}
