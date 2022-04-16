package frc.robot.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.*;
import frc.robot.Constants;

public class ShuffleboardUtils {

    public static ShuffleboardTab mainTab = Shuffleboard.getTab(Constants.ShuffleboardMainTabName);
    public static ShuffleboardTab autoTab = Shuffleboard.getTab(Constants.ShuffleboardAutoTabName);

    private static Map<String, NetworkTableEntry> simpleWidgets = new HashMap<String, NetworkTableEntry>();

    private static NetworkTableEntry makeEntry(ShuffleboardTab tab, String name, Object value, Consumer<Map.Entry<String, SimpleWidget>> initializeWidget) {
        NetworkTableEntry entry;
        if (simpleWidgets.containsKey(name))
        {
            entry = simpleWidgets.get(name);
        }
        else
        {
            SimpleWidget widget = tab.add(name, value);
            initializeWidget.accept(Map.entry(name, widget));
            entry = widget.getEntry();
            simpleWidgets.put(name, entry);
        }
        return entry;
    }

    public static void setBoolean(ShuffleboardTab tab, String name, boolean value, Consumer<Map.Entry<String, SimpleWidget>> initializeWidget) {
        makeEntry(tab, name, value, initializeWidget).setBoolean(value);
    }

    public static void setDouble(ShuffleboardTab tab, String name, double value, Consumer<Map.Entry<String, SimpleWidget>> initializeWidget) {
        makeEntry(tab, name, value, initializeWidget).setDouble(value);
    }

    public static void setString(ShuffleboardTab tab, String name, String value, Consumer<Map.Entry<String, SimpleWidget>> initializeWidget) {
        makeEntry(tab, name, value, initializeWidget).setString(value);
    }

    public static void setNumber(ShuffleboardTab tab, String name, Number value, Consumer<Map.Entry<String, SimpleWidget>> initializeWidget) {
        makeEntry(tab, name, value, initializeWidget).setNumber(value);
    }

}