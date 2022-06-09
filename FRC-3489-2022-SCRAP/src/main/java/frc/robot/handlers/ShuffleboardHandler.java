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
    public class Widget {
        private SimpleWidget widget;

        private Object cachedValue = null;

        private boolean timedUpdate = false;
        private Timer timer = new Timer();
        private double updatePeriod = 1;
        private double carryover = 0;

        public Widget(Tab tab, String title, Object defaultValue, Consumer<SimpleWidget> init) {
            cachedValue = defaultValue;
            widget = tab.get().add(title, defaultValue);
            if (init != null) {
                init.accept(widget);
            }
        }

        public Widget withTimedUpdate(double frequency) {
            timedUpdate = true;
            timer.start();
            updatePeriod = 1.0 / frequency;
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
        Diagnostic("Diagnostic"),
        Config("Config");

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

    private List<Widget> widgets = new ArrayList<Widget>();

    @Override
    public void robotPeriodic() {
        widgets.forEach(Widget::pollUpdate);
    }

    public Widget newWidget(Tab tab, String title, Object defaultValue, Consumer<SimpleWidget> init) {
        Widget widget = new Widget(tab, title, defaultValue, init);
        widgets.add(widget);
        return widget;
    }
}
