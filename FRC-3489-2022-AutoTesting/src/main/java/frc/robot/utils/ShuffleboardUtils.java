package frc.robot.utils;

import java.util.HashMap;
import java.util.Map;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.*;
import frc.robot.Constants;

public class ShuffleboardUtils {

    public static ShuffleboardTab mainTab = Shuffleboard.getTab(Constants.ShuffleboardMainTabName);
    public static ShuffleboardTab autoTab = Shuffleboard.getTab(Constants.ShuffleboardAutoTabName);

    private static Map<String, NetworkTableEntry> simpleWidgets = new HashMap<String, NetworkTableEntry>();

    private static NetworkTableEntry makeEntry(ShuffleboardTab tab, String name, Object value)
    {
        NetworkTableEntry entry;
        if (simpleWidgets.containsKey(name))
        {
            entry = simpleWidgets.get(name);
        }
        else
        {
            entry = tab.add(name, value).getEntry();
            simpleWidgets.put(name, entry);
        }
        return entry;
    }

    public static void setBoolean(ShuffleboardTab tab, String name, boolean value)
    {
        makeEntry(tab, name, value).setBoolean(value);
    }

    public static void setDouble(ShuffleboardTab tab, String name, double value)
    {
        makeEntry(tab, name, value).setDouble(value);
    }

    public static void setString(ShuffleboardTab tab, String name, String value)
    {
        makeEntry(tab, name, value).setString(value);
    }

}