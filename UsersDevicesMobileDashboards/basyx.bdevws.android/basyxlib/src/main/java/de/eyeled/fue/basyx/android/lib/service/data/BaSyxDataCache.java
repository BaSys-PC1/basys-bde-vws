package de.eyeled.fue.basyx.android.lib.service.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.eyeled.fue.basyx.android.lib.aas.AndroidAssetAdministrationShell;

public class BaSyxDataCache {
    private static final BaSyxDataCache sInstance = new BaSyxDataCache();
    public final static String MAIN = "main";

    protected HashMap<String, HashMap<String, AndroidAssetAdministrationShell>> mAndroidAssetAdministrationShellHashMap = new HashMap<>();

    private BaSyxDataCache(){}

    public static synchronized BaSyxDataCache i(){
        return sInstance;
    }

    public boolean hasAasType(String type){
        return mAndroidAssetAdministrationShellHashMap.containsKey(type);
    }

    public HashMap<String, AndroidAssetAdministrationShell> getAasTypeMap(String type){
        return mAndroidAssetAdministrationShellHashMap.get(type);
    }


    public List<AndroidAssetAdministrationShell> getAasList(String type){
        HashMap<String, AndroidAssetAdministrationShell> map =  getAasTypeMap(type);
        if(map != null){
            return new ArrayList<>(map.values());
        }

        return null;
    }

    public AndroidAssetAdministrationShell getAas(String id){
        for(String key : mAndroidAssetAdministrationShellHashMap.keySet()){
            AndroidAssetAdministrationShell aas = getAas(key,id);
            if(aas != null){
                return aas;
            }
        }

        return null;
    }

    public AndroidAssetAdministrationShell getAas(String type, String id){
        HashMap<String, AndroidAssetAdministrationShell> aasMap = mAndroidAssetAdministrationShellHashMap.get(type);
        if(aasMap != null && aasMap.containsKey(id)){
            return aasMap.get(id);
        }

        return null;
    }


    public synchronized void addAaas(AndroidAssetAdministrationShell aAas){
        addAaas(aAas, MAIN);
    }

    public synchronized void addAaas(AndroidAssetAdministrationShell aAas, String type){
        String  aasId = aAas.getId();
        if(aasId != null) {
            HashMap<String, AndroidAssetAdministrationShell> typeMap = mAndroidAssetAdministrationShellHashMap.get(type);
            if(typeMap == null){
                typeMap = new HashMap<>();
                mAndroidAssetAdministrationShellHashMap.put(type, typeMap);
            }

            typeMap.put(aasId, aAas);
        }
    }

    public synchronized void clear(){
        mAndroidAssetAdministrationShellHashMap.clear();
    }
}
