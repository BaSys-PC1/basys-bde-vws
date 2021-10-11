package de.eyeled.fue.basyx.android.bdevws.lib.repository.services;


import java.util.HashMap;

import de.eyeled.fue.basyx.lib.connection.ConnectionClient;
import de.eyeled.fue.basyx.lib.connection.ConnectionProperty;
import de.eyeled.fue.basyx.lib.connection.interfaces.ClientConnectionListener;
import de.eyeled.fue.basyx.lib.connection.netty.NettyClient;


public class CommunicationService {
    private static CommunicationService sInstance = new CommunicationService();
    private HashMap<ConnectionProperty, ConnectionClient> mClientList = new HashMap<>();

    private CommunicationService(){}

    public static synchronized CommunicationService i(){
        return sInstance;
    }

    public synchronized boolean startConnection(
            final ConnectionProperty property,
            final ClientConnectionListener listener){
        ConnectionClient connectionClient = initConnection(property, listener);
        if(connectionClient != null){
            connectionClient.start();
            return true;
        }

        return false;
    }

    public synchronized ConnectionClient initConnection(
            ConnectionProperty property, ClientConnectionListener listener){
        ConnectionClient client = mClientList.get(property);
        if(client == null) {
            client = getConnectionClient(property);
            mClientList.put(property,client);
        }

        if(client != null) {
            client.addClientConnectionListener(listener);
        }

        return client;
    }

    public synchronized void removeConnectionListener(ClientConnectionListener listener){
        for(ConnectionProperty property: mClientList.keySet() ){
            ConnectionClient client = mClientList.get(property);
            if(client != null) {
                client.removeClientConnectionListener(listener);
            }
        }
    }

    public synchronized void removeConnection(ConnectionProperty property){
        mClientList.remove(property);
    }

    public static ConnectionClient getConnectionClient(ConnectionProperty property){
        switch (property.getConnectionProviderType()){
            case NETTY:
                return new NettyClient(property);
            default:
                return null;
        }
    }
}
