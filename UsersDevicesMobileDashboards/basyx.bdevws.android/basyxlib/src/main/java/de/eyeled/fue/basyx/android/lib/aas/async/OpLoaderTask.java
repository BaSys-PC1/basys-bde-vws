package de.eyeled.fue.basyx.android.lib.aas.async;

import android.util.Log;

import org.eclipse.basyx.submodel.metamodel.api.submodelelement.operation.IOperation;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.operation.IOperationVariable;

import java.util.Collection;
import java.util.concurrent.RecursiveAction;

import de.eyeled.fue.basyx.android.lib.aas.AndroidOperation;
import de.eyeled.fue.basyx.android.lib.aas.AndroidOperationVariable;

public class OpLoaderTask extends RecursiveAction {

    private final IOperation mBasysOperation;
    private AndroidOperation mAndroidOperation;

    public OpLoaderTask(IOperation basysOperation) {
        this.mBasysOperation = basysOperation;
    }

    @Override
    protected void compute() {
        try {
            mAndroidOperation = new AndroidOperation(mBasysOperation);
            Collection<IOperationVariable> opIn = mBasysOperation.getInputVariables();
            Collection<IOperationVariable> opOut = mBasysOperation.getOutputVariables();
            Collection<IOperationVariable> opInOut = mBasysOperation.getInOutputVariables();

            if (opIn != null && opIn.size() > 0) {
                for (IOperationVariable operationVariable : opIn) {
                    AndroidOperationVariable in = new AndroidOperationVariable(operationVariable);
                    mAndroidOperation.getInVars().add(in);
                }
            }

            if (opOut != null && opOut.size() > 0) {
                for (IOperationVariable operationVariable : opOut) {
                    AndroidOperationVariable out = new AndroidOperationVariable(operationVariable);
                    mAndroidOperation.getOutVars().add(out);
                }
            }

            if (opInOut != null && opInOut.size() > 0) {
                for (IOperationVariable operationVariable : opInOut) {
                    AndroidOperationVariable inOut = new AndroidOperationVariable(operationVariable);
                    mAndroidOperation.getInOutVars().add(inOut);
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
            Log.e("OPLoaderTask", "error while loading operation: "+e.getMessage());
        }
    }

    public AndroidOperation getAndroidOperation() {
        return mAndroidOperation;
    }
}
