package de.eyeled.fue.basyx.android.bdevws.lib.repository.listener;


import de.eyeled.fue.basyx.android.lib.aas.AndroidOperation;
import de.eyeled.fue.basyx.android.lib.repository.listener.RepositoryListener;
import de.eyeled.fue.basyx.lib.connection.ConnectionProperty;

public interface BdeRepositoryListener extends RepositoryListener {
    void onNewOperationData(ConnectionProperty property, AndroidOperation operation, Object data);
    void onClientConnection(ConnectionProperty property, AndroidOperation operation, boolean connected);
    void onKafkaAddress(String address);
    void onBdeAddress(String address);
}
