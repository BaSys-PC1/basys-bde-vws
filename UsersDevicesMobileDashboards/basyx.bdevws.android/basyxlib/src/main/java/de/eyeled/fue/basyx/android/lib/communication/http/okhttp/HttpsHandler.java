package de.eyeled.fue.basyx.android.lib.communication.http.okhttp;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.OkUrlFactory;

import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.util.HashMap;

public final class HttpsHandler extends URLStreamHandler {

    HashMap<String, String> urlOverride;

    public HttpsHandler(HashMap<String, String> urlOverride) {
        this.urlOverride = urlOverride;
    }
    public HttpsHandler() {}

    @Override protected URLConnection openConnection(URL url) {
        OkHttpClient client = new OkHttpClient();
        client.setRetryOnConnectionFailure(true);
        return new OkUrlFactory(client).open(url);
    }
    @Override protected URLConnection openConnection(URL url, Proxy proxy) {
        if (url == null || proxy == null) {
            throw new IllegalArgumentException("url == null || proxy == null");
        }
        OkHttpClient client = new OkHttpClient();
        client.setRetryOnConnectionFailure(true);
        return new OkUrlFactory(client.setProxy(proxy)).open(url);
    }
    @Override protected int getDefaultPort() {
        return 443;
    }
}
