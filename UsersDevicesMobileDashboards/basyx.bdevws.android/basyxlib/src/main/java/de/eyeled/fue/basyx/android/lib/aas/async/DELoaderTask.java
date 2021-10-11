package de.eyeled.fue.basyx.android.lib.aas.async;

import android.util.Log;

import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElement;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElement;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RecursiveAction;

import de.eyeled.fue.basyx.android.lib.aas.AndroidDataElement;


public class DELoaderTask extends RecursiveAction {

    private AndroidDataElement mAndroidDataElement;
    private final ISubmodelElement mBasysSubModelElement;
    private boolean mLoadData;

    public DELoaderTask(ISubmodelElement mBasysSubmodelElement,
                        boolean loadData) {
        this.mBasysSubModelElement = mBasysSubmodelElement;
        this.mLoadData = loadData;
    }

    @Override
    protected void compute() {
        try {
            mAndroidDataElement = new AndroidDataElement(mBasysSubModelElement, false);
           // Log.d("DELoaderTask", "Loading Element Data: "+mAndroidDataElement.getIdShort());
            if (mLoadData) {
                if (mAndroidDataElement.isPropertyElement()) {
                    mAndroidDataElement.loadPropertyValue();
                }
                else if(mAndroidDataElement.getElementCollection() != null){
                    List<DELoaderTask> deLoadertasks = new ArrayList<>();
                    Map<String, ISubmodelElement> submodelElements = null;
                    Collection<Property> propertyCollection = null;
                    try {
                        submodelElements = mAndroidDataElement.getElementCollection().getSubmodelElements();
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                        Log.d("DeLoaderTask","error "+e.getMessage());
                    }
                    if(submodelElements == null) {
                        try {
                            SubmodelElement localCopy = mAndroidDataElement.getElementCollection().getLocalCopy();
                            Object copyValue = localCopy.getValue();
                            if(copyValue instanceof Collection){
                                propertyCollection = (Collection) copyValue;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.d("DeLoaderTask", "error " + e.getMessage());
                        }
                    }


                    try {
                        mAndroidDataElement.getCollectionDataElements().clear();

                        if(submodelElements != null){
                            for(ISubmodelElement element : submodelElements.values()){
                                if (element != null) {
                                    DELoaderTask deTask = new DELoaderTask(element, true);
                                    deLoadertasks.add(deTask);
                                    deTask.fork();
                                }
                            }
                        }
                        else if(propertyCollection != null){
                            for(ISubmodelElement element : propertyCollection){
                                if (element != null) {
                                    DELoaderTask deTask = new DELoaderTask(element, true);
                                    deLoadertasks.add(deTask);
                                    deTask.fork();
                                }
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.d("DeLoaderTask","error "+e.getMessage());
                    }

                    if (deLoadertasks.size() > 0) {
                        for (DELoaderTask task : deLoadertasks) {
                            task.join();
                            AndroidDataElement dataElement = task.getAndroidDataElement();
                            if (dataElement != null) {
                                mAndroidDataElement.getCollectionDataElements().add(dataElement);
                            } else {
                                Log.e("DeLoaderTask", "a data element could not be loaded");
                            }
                        }
                    }
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
            Log.e("DELoaderTask", "error while loading dataelement: "+e.getMessage());
        }
       // Log.d("DELoaderTask", "Loading Element Data done: "+mAndroidDataElement.getIdShort());
    }

    public AndroidDataElement getAndroidDataElement() {
        return mAndroidDataElement;
    }
}
