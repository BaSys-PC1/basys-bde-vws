package de.eyeled.fue.basyx.android.lib.repository.listener;


import de.eyeled.fue.basyx.android.lib.repository.data.BaSyxError;
import de.eyeled.fue.basyx.android.lib.service.BaSyxService;

public interface RepositoryListener {
    void onError(BaSyxError error);
    void onStatusUpdated(BaSyxService.Status status);
}
