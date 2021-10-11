package de.eyeled.fue.basyx.android.lib.communication.http;

import android.util.Log;

import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;
import java.util.HashMap;

import de.eyeled.fue.basyx.android.lib.communication.http.okhttp.HttpHandler;
import de.eyeled.fue.basyx.android.lib.communication.http.okhttp.HttpsHandler;

/**
 * Custom Url Stream Handler Factory to be used in cases the default
 * Http client initialisation needs to be changed via
 * URL.setURLStreamHandlerFactory()
 * This class only use custom handler for HTTP & HTTPS (not file, jar, etc)
 */
public class CustomURLStreamHandlerFactory implements URLStreamHandlerFactory {
    HashMap<String, String> urlOverride;

    public CustomURLStreamHandlerFactory(HashMap<String, String> urlOverride) {
        this.urlOverride = urlOverride;
    }

    public CustomURLStreamHandlerFactory() {}

    @Override
    public URLStreamHandler createURLStreamHandler(String protocol) {
        URLStreamHandler handler = null;
        try {
            if (protocol.equals("http")) {
                handler = new HttpHandler(urlOverride);
            } else if (protocol.equals("https")) {
                handler = new HttpsHandler(urlOverride);
            }
        }
        catch (Exception e){
            e.printStackTrace();
            Log.e("CstURLStreamHandlerFac", "error in BdeURLStreamHandlerFactory: "+e.getMessage());
        }
        return handler;
    }
}
