package frc.robot.handlers;

public static class CSVUtils {

    private static Map<String, String> files = new HashMap<String, String>();
    // use files.putIfAbsent(key, val);

    public static void setColumns(String csv, String... columns) {
        String columnsRow = "";
        for (int i = 0; i < columns.length - 2; i++) {
            columnsRow += columns[i] + ",";
        }
        columnsRow += columns[columns.length - 1];
        files.putIfAbsent(csv, columnsRow + "\n");
    }
}