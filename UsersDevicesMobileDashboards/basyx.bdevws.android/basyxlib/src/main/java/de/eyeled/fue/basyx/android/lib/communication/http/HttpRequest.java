package de.eyeled.fue.basyx.android.lib.communication.http;

import android.graphics.Bitmap;
import android.widget.ImageView;


import org.json.JSONObject;

import de.eyeled.fue.basyx.android.lib.communication.data.RequestType;
import de.eyeled.fue.basyx.lib.connection.data.DataType;

public class HttpRequest {
    private String mUrl;
    private String mId;
    private RequestType mRequestType;
    private DataType mDataType;
    private String mPostData;
    private JSONObject mJsonRequest;

    // Bitmap Data
    private int mMaxWidth;
    private int mMaxHeight;
    private Bitmap.Config mDecodeConfig;
    private ImageView.ScaleType mScaleType;

    public HttpRequest(String mUrl, String id, RequestType mRequestType, DataType mDataType) {
        this.mUrl = mUrl;
        this.mId = id;
        this.mRequestType = mRequestType;
        this.mDataType = mDataType;
    }

    public String getPostData() {
        return mPostData;
    }

    public HttpRequest setPostData(String mPostData) {
        this.mPostData = mPostData;
        return this;
    }

    public JSONObject getJsonRequest() {
        return mJsonRequest;
    }

    public HttpRequest setJsonRequest(JSONObject mJsonRequest) {
        this.mJsonRequest = mJsonRequest;
        return this;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String mUrl) {
        this.mUrl = mUrl;
    }

    public RequestType getRequestType() {
        return mRequestType;
    }

    public void setRequestType(RequestType mRequestType) {
        this.mRequestType = mRequestType;
    }

    public DataType getDataType() {
        return mDataType;
    }

    public void setDataType(DataType mDataType) {
        this.mDataType = mDataType;
    }

    public int getMaxWidth() {
        return mMaxWidth;
    }

    public void setmMaxWidth(int mMaxWidth) {
        this.mMaxWidth = mMaxWidth;
    }

    public int getMaxHeight() {
        return mMaxHeight;
    }

    public void setMaxHeight(int mMaxHeight) {
        this.mMaxHeight = mMaxHeight;
    }

    public Bitmap.Config getDecodeConfig() {
        return mDecodeConfig;
    }

    public void setDecodeConfig(Bitmap.Config mDecodeConfig) {
        this.mDecodeConfig = mDecodeConfig;
    }

    public ImageView.ScaleType getScaleType() {
        return mScaleType;
    }

    public void setScaleType(ImageView.ScaleType mScaleType) {
        this.mScaleType = mScaleType;
    }

    public String getId() {
        return mId;
    }

    public void setId(String mId) {
        this.mId = mId;
    }
}
