package project.config;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class ConfigFileMaker {
    private static final String fileLocation = "src/main/resources/config.properties";
    private static FileWriter fileWriter = null;

    static {
        try {
            File jarFile = new File("src/main/resources/config.properties");
            if(jarFile.createNewFile()) {
                System.err.println("No config file exists! \nMaking config file.");
            } else {
                System.out.println("Config file exists.");
            }
            fileWriter = new FileWriter(fileLocation);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createDefaultConfig() {
        Properties properties = new Properties();
        properties.setProperty("token", "");
        try {
            properties.store(fileWriter, "");
        } catch (IOException e) {
            System.err.println("Failed to write to config file!");
            e.printStackTrace();
        }
    }
}
