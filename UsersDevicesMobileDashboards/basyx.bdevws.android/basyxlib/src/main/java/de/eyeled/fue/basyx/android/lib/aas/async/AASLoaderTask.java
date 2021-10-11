package de.eyeled.fue.basyx.android.lib.aas.async;

import android.util.Log;

import org.eclipse.basyx.aas.metamodel.api.IAssetAdministrationShell;
import org.eclipse.basyx.submodel.metamodel.api.ISubmodel;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

import de.eyeled.fue.basyx.android.lib.aas.AndroidAssetAdministrationShell;
import de.eyeled.fue.basyx.android.lib.aas.AndroidSubModel;


public class AASLoaderTask extends RecursiveTask<AndroidAssetAdministrationShell> {

    private AndroidAssetAdministrationShell mAndroidAas;
    private IAssetAdministrationShell mBasysAas;
    private boolean mLoadSubModels;

    public AASLoaderTask(@NotNull IAssetAdministrationShell aas, boolean loadSubModels) {
        mBasysAas = aas;
        mLoadSubModels = loadSubModels;
    }

    public AASLoaderTask(@NotNull AndroidAssetAdministrationShell aAas, boolean loadSubModels) {
        mAndroidAas = aAas;
        mLoadSubModels = loadSubModels;
    }

    @Override
    protected AndroidAssetAdministrationShell compute() {
        Log.d("AASLoaderTask", "compute AndroidAssetAdministrationShell "+mBasysAas);
        if(mAndroidAas == null) {
            // create the android aas
            mAndroidAas = new AndroidAssetAdministrationShell(mBasysAas);
        }

        if(mLoadSubModels) {
            mAndroidAas.getAndroidSubModelList().clear();
            Map<String, ISubmodel> subModelMap = mAndroidAas.getAssetAdministrationShell().getSubmodels();
            List<SMLoaderTask> tasks = new ArrayList<>();

            for (ISubmodel subModel : subModelMap.values()) {
                SMLoaderTask smTask = new SMLoaderTask(subModel, mAndroidAas, true);
                tasks.add(smTask);
                smTask.fork();
            }

            if (tasks.size() > 0) {
                for (SMLoaderTask task : tasks) {
                    task.join();
                    AndroidSubModel sm = task.getAndroidSm();
                    if(sm != null) {
                        mAndroidAas.addSubModel(task.getAndroidSm());
                    }
                    else {
                        Log.e("AASLoaderTask","error while loading sm");
                    }
                }
            }
        }

        return mAndroidAas;
    }

    public AndroidAssetAdministrationShell getAndroidAas() {
        return mAndroidAas;
    }
}
