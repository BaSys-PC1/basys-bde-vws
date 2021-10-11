package de.eyeled.fue.basyx.android.lib.service.components;


import org.eclipse.basyx.aas.manager.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.registration.api.IAASRegistry;
import org.eclipse.basyx.aas.registration.proxy.AASRegistryProxy;
import org.eclipse.basyx.vab.protocol.http.connector.HTTPConnectorFactory;

public class RegistryComponent {
    private String mAasType;
    private ConnectedAssetAdministrationShellManager mManager;
    private IAASRegistry mRegistryService;
    private String mRegistryServiceAddress;

    private long mLastUpdated;

    public RegistryComponent(String mAasType,
                             String registryServiceAddress) {
        this.mAasType = mAasType;
        this.mRegistryService = new AASRegistryProxy(registryServiceAddress);
        this.mRegistryServiceAddress = registryServiceAddress;
        this.mManager = new ConnectedAssetAdministrationShellManager(
                this.mRegistryService, new HTTPConnectorFactory());
    }

    public String getAasType() {
        return mAasType;
    }

    public ConnectedAssetAdministrationShellManager getManager() {
        return mManager;
    }

    public IAASRegistry getRegistryService() {
        return mRegistryService;
    }

    public String getRegistryServiceAddress() {
        return mRegistryServiceAddress;
    }

    public long getLastUpdated() {
        return mLastUpdated;
    }

    public void setLastUpdated(long mLastUpdated) {
        this.mLastUpdated = mLastUpdated;
    }
}
