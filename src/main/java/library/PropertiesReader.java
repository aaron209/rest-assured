package library;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesReader {

    protected static Properties properties;
    protected static FileInputStream fileInputStream;

    public static String readConfigFile(String key) throws IOException {
        properties = new Properties();
        fileInputStream = new FileInputStream(FilePathConstant.CONFIG_FILE_PATH);
        properties.load(fileInputStream);
        return properties.getProperty(key).trim();
    }

}
