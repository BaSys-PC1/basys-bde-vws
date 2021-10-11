package de.eyeled.fue.basyx.android.bdevws.lib.repository.services;

import android.content.Context;

import org.json.JSONObject;

import de.eyeled.fue.basyx.android.lib.communication.data.RequestType;
import de.eyeled.fue.basyx.android.lib.communication.http.HttpRequest;
import de.eyeled.fue.basyx.android.lib.communication.http.HttpRequestProvider;
import de.eyeled.fue.basyx.android.lib.communication.http.HttpRequester;
import de.eyeled.fue.basyx.android.lib.communication.http.HttpResponseListener;
import de.eyeled.fue.basyx.lib.connection.data.DataType;

public class BdeBackendCommunication {
    public static final String DEVICE_REGISTRATION_REQUEST = "deviceRegistration";
    private static BdeBackendCommunication sInstance = new BdeBackendCommunication();

    private BdeBackendCommunication(){}

    public static synchronized BdeBackendCommunication i(){
        return sInstance;
    }

    public synchronized void registerDevice(Context context, HttpResponseListener listener,
                                            String endpoint, JSONObject data){
        HttpRequester.i(context).addToRequestQueue(HttpRequestProvider.getRequest(
                        new HttpRequest(
                                endpoint, DEVICE_REGISTRATION_REQUEST,
                                RequestType.POST, DataType.JSON)
                        .setJsonRequest(data),listener));
    }
}
