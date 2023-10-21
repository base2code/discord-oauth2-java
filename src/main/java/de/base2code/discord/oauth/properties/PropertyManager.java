package de.base2code.discord.oauth.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyManager {
    private Properties properties;

    private void load() throws IOException {
        InputStream resourceAsStream = this.getClass().getResourceAsStream("/project.properties");
        java.util.Properties prop = new java.util.Properties();
        prop.load(resourceAsStream);
        properties = prop;
    }

    public String getVersion() throws IOException {
        if (properties == null) load();
        return properties.getProperty("version");
    }

    public String getBaseUri() throws IOException {
        if (properties == null) load();
        return properties.getProperty("baseUri");
    }
}
