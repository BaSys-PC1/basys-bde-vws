package de.eyeled.fue.basyx.android.lib.helper;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyFile {
    private static final String PROPERTY_FILE = "config";

    public static String getProperty(String key, Context context) throws IOException {
        return getProperty(PROPERTY_FILE, key, context);
    }

    public static String getProperty(String propertyFileName, String key, Context context) throws IOException {
        Properties properties = new Properties();
        AssetManager assetManager = context.getAssets();
        InputStream inputStream = assetManager.open(propertyFileName);
        properties.load(inputStream);
        return properties.getProperty(key);
    }
}
