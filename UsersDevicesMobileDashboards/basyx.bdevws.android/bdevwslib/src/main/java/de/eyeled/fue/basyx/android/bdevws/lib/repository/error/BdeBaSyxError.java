package de.eyeled.fue.basyx.android.bdevws.lib.repository.error;

import de.eyeled.fue.basyx.android.lib.repository.data.BaSyxError;

public class BdeBaSyxError extends BaSyxError {
    private Type mErrorType;

    public BdeBaSyxError(Type errorType) {
        mErrorType = errorType;
    }

    public BdeBaSyxError() {}

    public Type getType() {
        return mErrorType;
    }

    public void setBdeType(Type errorType) {
        mErrorType = errorType;
    }

    public enum  Type{
        VIEW, CONNECTION, CONNECTION_DEVICE_SETUP,
        CONNECTION_NO_USER_AAS_SERVER, CONNECTION_NO_DEVICE_AAS_SERVER
    }
}
