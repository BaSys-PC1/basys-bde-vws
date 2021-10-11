package de.eyeled.fue.basyx.android.lib.aas.async;

import android.util.Log;

import org.eclipse.basyx.submodel.metamodel.api.ISubmodel;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElement;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.operation.IOperation;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RecursiveAction;

import de.eyeled.fue.basyx.android.lib.aas.AndroidAssetAdministrationShell;
import de.eyeled.fue.basyx.android.lib.aas.AndroidDataElement;
import de.eyeled.fue.basyx.android.lib.aas.AndroidOperation;
import de.eyeled.fue.basyx.android.lib.aas.AndroidSubModel;


public class SMLoaderTask extends RecursiveAction {
    private AndroidSubModel mAndroidSm;
    private AndroidAssetAdministrationShell mAndroidAas;
    private ISubmodel mBasysSm;
    private boolean mLoadData;

    public SMLoaderTask(@NotNull ISubmodel subModel,
                        @NotNull AndroidAssetAdministrationShell androidAas,
                        boolean loadData) {
        mBasysSm = subModel;
        mAndroidAas = androidAas;
        mLoadData = loadData;
    }

    @Override
    protected void compute() {
        try {
            mAndroidSm = new AndroidSubModel(mBasysSm, mAndroidAas, false);
            Log.d("SMLoaderTask", "compute AndroidSubModel " + mAndroidSm.getId());

            if (mLoadData) {
                Map<String, ISubmodelElement> subModelElements = mAndroidSm.getBaSyxSubModel().getSubmodelElements();
                List<DELoaderTask> deLoadertasks = new ArrayList<>();

                if (subModelElements != null && !subModelElements.isEmpty()) {
                    for (String key : subModelElements.keySet()) {
                        ISubmodelElement element = subModelElements.get(key);
                        if (element != null) {
                            DELoaderTask deTask = new DELoaderTask(element, true);
                            deLoadertasks.add(deTask);
                            deTask.fork();
                        }
                    }
                }


                Map<String, IOperation> operationMap = mAndroidSm.getBaSyxSubModel().getOperations();
                List<OpLoaderTask> opLoaderTasks = new ArrayList<>();

                if (operationMap != null && !operationMap.isEmpty()) {
                    for (String key : operationMap.keySet()) {
                        IOperation element = operationMap.get(key);
                        if (element != null) {
                            OpLoaderTask opTask = new OpLoaderTask(element);
                            opLoaderTasks.add(opTask);
                            opTask.fork();
                        }
                    }
                }

                if (deLoadertasks.size() > 0) {
                    for (DELoaderTask task : deLoadertasks) {
                        task.join();
                        AndroidDataElement dataElement = task.getAndroidDataElement();
                        if (dataElement != null) {
                            mAndroidSm.addDataElement(task.getAndroidDataElement());
                        } else {
                            Log.e("SMLoaderTask", "a data element could not be loaded");
                        }
                    }
                }

                if (opLoaderTasks.size() > 0) {
                    for (OpLoaderTask task : opLoaderTasks) {
                        task.join();
                        AndroidOperation aop = task.getAndroidOperation();
                        if (aop != null) {
                            mAndroidSm.addOperation(aop);
                            aop.setAndroidSubModel(mAndroidSm);
                        }
                    }
                }

            }
        }
        catch (Exception e){
            e.printStackTrace();
            Log.e("SMLoaderTask", "error while loading submodel: "+e.getMessage());
        }
    }

    public AndroidSubModel getAndroidSm() {
        return mAndroidSm;
    }
}
