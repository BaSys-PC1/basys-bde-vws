package de.eyeled.fue.basyx.android.lib.communication.http;

import com.android.volley.Request;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import de.eyeled.fue.basyx.android.lib.communication.data.RequestType;
import de.eyeled.fue.basyx.lib.connection.data.DataType;

public class HttpRequestProvider {

    public static Request<?> getRequest(HttpRequest request, HttpResponseListener listener){
        switch (request.getDataType()){
            case JSON:
                return createJsonRequest(request, listener);
            case STRING:
                return createStringRequest(request, listener);
            case IMAGE:
                return createImageRequest(request, listener);
        }

        return null;
    }

    protected static JsonObjectRequest createJsonRequest(
            HttpRequest request, HttpResponseListener listener){
        return new JsonObjectRequest
            (getRequestType(request.getRequestType()), request.getUrl(), request.getJsonRequest(),
                    response -> listener.onHttpResponse(response, DataType.JSON,request.getId(),request.getUrl()),
                    error -> listener.onHttpResponse(error.getMessage(), DataType.ERROR,request.getId(),request.getUrl()));
    }

    protected static StringRequest createStringRequest(
            HttpRequest request, HttpResponseListener listener){
        return new StringRequest(getRequestType(request.getRequestType()), request.getUrl(),
                response -> listener.onHttpResponse(response, DataType.STRING,request.getId(),request.getUrl()),
                error -> listener.onHttpResponse(error.getMessage(), DataType.ERROR,request.getId(),request.getUrl()));
    }
    protected static ImageRequest createImageRequest(
            HttpRequest request, HttpResponseListener listener){
        return new ImageRequest(
                request.getUrl(),
                response -> listener.onHttpResponse(response, DataType.IMAGE,request.getId(),request.getUrl()),
                request.getMaxWidth(),
                request.getMaxHeight(),
                request.getScaleType(),
                request.getDecodeConfig(),
                error -> listener.onHttpResponse(error.getMessage(), DataType.ERROR,request.getId(),request.getUrl()));
    }

    protected static int getRequestType(RequestType requestType){
        switch (requestType){
            case DELETE:
                return Request.Method.DELETE;
            case POST:
                return Request.Method.POST;
            case PUT:
                return Request.Method.PUT;
            case GET:
            default:
                return Request.Method.GET;
        }
    }
}
