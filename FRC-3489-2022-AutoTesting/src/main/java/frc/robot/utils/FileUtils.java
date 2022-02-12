package frc.robot.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.wpilibj.Filesystem;

public class FileUtils {
    
    public static void printDir(String path) {
        String[] dir = getFile(path, "").list();
        for (String f : dir) {
            System.out.println(f);
        }
    }

    public static void writeFile(String path, String file, String data)
    {
        if (!fileExists(path, file))
            createFile(path, file);

        File f = new File(appendPath(path, file));
        Charset charset = Charset.forName("US-ASCII");
        try (BufferedWriter writer = Files.newBufferedWriter(f.toPath(), charset)) {
            writer.write(data, 0, data.length());
        } catch (IOException e) {
            e.printStackTrace();  
        }
    }

    public static List<String> readFile(String path, String file)
    {
        List<String> lines = new ArrayList<String>();
        try {
            File f = new File(appendPath(path, file));
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while((line = br.readLine()) != null) {  
                lines.add(line);
            }  
            fr.close();
        }  
        catch(IOException e) {  
            e.printStackTrace();  
        }  
        return lines;
    }

    public static boolean fileExists(String path, String file) {
        File f = getFile(path, file);
        return f.exists();
    }

    private static void createFile(String path, String file) {
        try {
            File f = getFile(path, file);
            f.createNewFile();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static File getFile(String path, String file) {
        return new File(appendPath(path, file));
    }

    public static String operatingDir() {
        return Filesystem.getOperatingDirectory().toPath().toString();
    }

    public static String deployDir() {
        return Filesystem.getDeployDirectory().toPath().toString();
    }

    public static String appendPath(String path, String file) {
        return path + "/" + file;
    }

}