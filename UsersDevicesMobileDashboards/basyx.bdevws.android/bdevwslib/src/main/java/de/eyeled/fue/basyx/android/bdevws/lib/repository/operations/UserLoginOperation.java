package de.eyeled.fue.basyx.android.bdevws.lib.repository.operations;


import android.util.Log;

import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import de.eyeled.fue.basyx.android.bdevws.lib.aas.UserAndroidAAS;
import de.eyeled.fue.basyx.android.bdevws.lib.service.data.BdeBaSyxAasTypes;
import de.eyeled.fue.basyx.android.lib.aas.AndroidAssetAdministrationShell;
import de.eyeled.fue.basyx.android.lib.repository.interfaces.AasRepository;
import de.eyeled.fue.basyx.android.lib.repository.operations.InvokeOperation;
import de.eyeled.fue.basyx.android.lib.service.components.RegistryComponent;
import de.eyeled.fue.basyx.lib.aas.user.UserAssetAdministrationShell;


public class UserLoginOperation implements InvokeOperation {

    public UserLoginOperation() {}

    @Override
    public Object invoke(AasRepository repository, Object... objects) {
        Log.d("UserLoginOperation","invoke ");
        String password = null;
        String uName = null;

        if(objects != null && objects.length == 2){
            uName = objects[0] instanceof String ? (String) objects[0] : null;
            password = objects[1] instanceof String ? (String) objects[1] : null;
        }

        if(uName != null && password != null) {
            String userUrn = (new ModelUrn(
                    UserAssetAdministrationShell.LEGAL_ENTITY,
                    UserAssetAdministrationShell.SUBUNIT,
                    UserAssetAdministrationShell.SUBMODEL,
                    UserAssetAdministrationShell.VERSION,
                    UserAssetAdministrationShell.REVISION,
                    uName,
                    UserAssetAdministrationShell.INSTANCE)).getURN();

            Log.d("UserLoginOp", "user login: "+userUrn);

            Future<RegistryComponent> registryComponentFuture = fetchUserRegistryData(repository);

            try {
                registryComponentFuture.get(120, TimeUnit.SECONDS);
            } catch (Exception e) {
                e.printStackTrace();
            }



            // get the cached Android AAS List
            AndroidAssetAdministrationShell aas = repository.loadAndGetAas(userUrn, true);
            if (aas instanceof UserAndroidAAS) {
                if(((UserAndroidAAS) aas).checkUsernameAndPassword(uName,password)){
                    return aas;
                }
            }
        }
        return null;
    }

    private final ExecutorService executor
            = Executors.newSingleThreadExecutor();

    public Future<RegistryComponent> fetchUserRegistryData(AasRepository repository) {
        return executor.submit(() -> {
            // get the registry for the given user id urn
            RegistryComponent registryComponent = repository.getAasRegistryByType(BdeBaSyxAasTypes.USER_AAS_TYPE);

            // update the registry aas when the last update time is too big (10 minutes)
            if(registryComponent != null &&
                    System.currentTimeMillis() - registryComponent.getLastUpdated() > 600000) {
                try {
                    Log.d("UserLoginOp", "user registry update not valid. (re)loading aas data.");
                    repository.loadAndGetAasList(registryComponent, true, false);
                }
                catch (Exception ie){
                    Log.e("UserLoginOp", "error: "+ie.getMessage());
                }
            }
            else {
                if(registryComponent != null) {
                    Log.d("UserLoginOp", "user registry data valid " + (System.currentTimeMillis() - registryComponent.getLastUpdated()));
                }
                else {
                    Log.d("UserLoginOp", "user registry invalid / not found for  " );
                }
            }
            return registryComponent;
        });
    }
}
