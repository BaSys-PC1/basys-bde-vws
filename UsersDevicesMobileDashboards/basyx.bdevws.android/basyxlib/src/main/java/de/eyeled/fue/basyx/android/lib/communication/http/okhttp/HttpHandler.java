package de.eyeled.fue.basyx.android.lib.communication.http.okhttp;

import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.OkUrlFactory;

import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.util.HashMap;

/**
 * A custom Http Handler which initializes an OkHttpClient for URL request.
 * The default behavior for the OkHttpClient is changed as the
 * setRetryOnConnectionFailure parameter is set.
 * We need this in basyx connection context as a connection could fail.
 */
public final class HttpHandler extends URLStreamHandler {
    HashMap<String, String> urlOverride;

    public HttpHandler(HashMap<String, String> urlOverride) {
        this.urlOverride = urlOverride;
    }
    public HttpHandler() {}

    @Override protected URLConnection openConnection(URL url)  {
        OkHttpClient client = new OkHttpClient();
        client.setRetryOnConnectionFailure(true);
        return new OkUrlFactory(client).open(validateUrl(url));
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
        return 80;
    }

    private URL validateUrl(URL url){
        if(urlOverride != null) {
            String path = url.getPath();
            if(urlOverride.containsKey(path)){
                String newPath = urlOverride.get(path);
                URL updatedUrl = null;
                String query = url.getQuery() != null && !url.getQuery().isEmpty() ? "?" + url.getQuery() : "";
                try {
                    updatedUrl = new URL(url.getProtocol(), url.getHost(), url.getPort(), newPath + query, null);
                }
                catch (Exception e){
                    Log.e("HTTPHANDLER",e.getMessage());
                }
                if(updatedUrl != null){
                    return updatedUrl;
                }
            }
        }

        return url;
    }
}