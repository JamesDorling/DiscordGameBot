package project.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Config {
    private static final Properties config;
    private static final String fileLocation = "src/main/resources/config.properties";


    static {
        File configFile = new File(fileLocation);
        if (!configFile.exists()) {
            ConfigFileMaker.createDefaultConfig();
        }
        config = new Properties();
        try {
            config.load(new BufferedReader(new FileReader(fileLocation)));
        } catch (IOException e) {
            System.err.println("Failed to load the config file properly.");
            System.exit(0);
        }
    }

    public static String botToken() {return config.getProperty("token");}

}
