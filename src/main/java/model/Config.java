package model;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Properties;

public class Config {

    Properties prop = new Properties();
    String fileName = "app.config";

    public Config(String fileName) throws IOException {
        FileInputStream input = new FileInputStream(new File(fileName));
        prop.load(new InputStreamReader(input, Charset.forName("UTF-8")));
    }

    public void write() throws IOException {
        prop.store(new FileWriter(fileName), "#properties list");
    }

    public Properties getProp(){
        return prop;
    }


}
