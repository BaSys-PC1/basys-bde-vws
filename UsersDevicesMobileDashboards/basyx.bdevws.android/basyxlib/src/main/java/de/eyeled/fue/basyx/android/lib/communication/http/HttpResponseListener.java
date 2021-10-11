package de.eyeled.fue.basyx.android.lib.communication.http;

import de.eyeled.fue.basyx.lib.connection.data.DataType;

public interface HttpResponseListener {
    void onHttpResponse(Object data, DataType type, String id, String host);
}
