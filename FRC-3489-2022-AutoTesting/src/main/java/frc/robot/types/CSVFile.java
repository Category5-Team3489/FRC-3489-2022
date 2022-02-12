package frc.robot.types;

public class CSVFile {
    
    private String columnsRow = "";
    private String data = "";

    public void setColumns(String... columns) {
        columnsRow = "";
        for (int i = 0; i < columns.length - 2; i++) {
            columnsRow += columns[i] + ",";
        }
        columnsRow += columns[columns.length - 1] + "\n";
        data = columnsRow;
    }

    public void add(String line) {
        data += line + "\n";
    }

    public String get() {
        return data;
    }

    public void wipe() {
        data = columnsRow;
    }

}
