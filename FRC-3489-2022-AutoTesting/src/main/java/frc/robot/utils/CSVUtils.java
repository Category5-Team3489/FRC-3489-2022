package frc.robot.utils;

import java.util.HashMap;
import java.util.Map;

import frc.robot.types.CSVFile;

public class CSVUtils {

    private static Map<String, CSVFile> files = new HashMap<String, CSVFile>();

    public static void setColumns(String csv, String... columns) {
        if (files.containsKey(csv)) {
            files.get(csv).setColumns(columns);
        }
        else {
            CSVFile csvFile = new CSVFile();
            csvFile.setColumns(columns);
            files.put(csv, csvFile);
        }
    }

    public static void add(String csv, String line) {
        if (files.containsKey(csv)) {
            files.get(csv).add(line);
        }
    }

    public static void write(String csv, boolean wipe) {
        if (files.containsKey(csv)) {
            CSVFile csvFile = files.get(csv);
            int files = FileUtils.getFile(FileUtils.operatingDir(), "").list().length;
            FileUtils.writeFile(FileUtils.operatingDir(), files + csv, csvFile.get());
            System.out.println(csvFile.get());
            if (wipe)
                csvFile.wipe();
        }
    }
}