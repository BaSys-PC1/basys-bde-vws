package de.eyeled.fue.basyx.android.lib.aas.async.callable;

import org.eclipse.basyx.aas.metamodel.api.IAssetAdministrationShell;
import org.eclipse.basyx.submodel.metamodel.api.ISubmodel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import de.eyeled.fue.basyx.android.lib.aas.AndroidAssetAdministrationShell;
import de.eyeled.fue.basyx.android.lib.aas.AndroidSubModel;

public class AASCallable extends AndroidAssetAdministrationShell
        implements Callable<AndroidAssetAdministrationShell> {


    public AASCallable(@NotNull IAssetAdministrationShell aas, boolean loadSubModels) {
        super(aas);
    }


    @Override
    public AndroidAssetAdministrationShell call() throws Exception {
        return this;
    }

    @Nullable
    @Override
    public void loadSubModels() {
        ExecutorService executor = (ExecutorService) Executors.newFixedThreadPool(2);
        Map<String, ISubmodel> subModelMap = getAssetAdministrationShell().getSubmodels();
        List<ASMCallable> taskList = new ArrayList<>();

        for(ISubmodel value : subModelMap.values()){
            ASMCallable aSm = new ASMCallable(value, this, true);
            taskList.add(aSm);
        }

        List<Future<AndroidSubModel>> resultList = null;

        try {
            resultList = executor.invokeAll(taskList);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < resultList.size(); i++) {
            Future<AndroidSubModel> future = resultList.get(i);
            try {
                addSubModel(future.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        onSubModelsLoaded();
    }
}
