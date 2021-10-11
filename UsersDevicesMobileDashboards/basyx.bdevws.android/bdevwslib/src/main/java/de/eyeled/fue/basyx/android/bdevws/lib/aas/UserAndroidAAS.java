package de.eyeled.fue.basyx.android.bdevws.lib.aas;

import android.util.Log;

import de.eyeled.fue.basyx.android.bdevws.lib.service.data.BdeBaSyxAasTypes;
import de.eyeled.fue.basyx.android.lib.aas.AndroidAssetAdministrationShell;
import de.eyeled.fue.basyx.android.lib.aas.AndroidDataElement;
import de.eyeled.fue.basyx.android.lib.aas.AndroidOperation;
import de.eyeled.fue.basyx.android.lib.aas.AndroidSubModel;
import de.eyeled.fue.basyx.lib.aas.user.UserRoleSubModel;
import de.eyeled.fue.basyx.lib.aas.user.UserSkillSubModel;
import de.eyeled.fue.basyx.lib.aas.user.UserStammDatenSubModel;
import de.eyeled.fue.basyx.lib.aas.user.UserViewSubModel;

import org.eclipse.basyx.aas.metamodel.api.IAssetAdministrationShell;

import java.util.List;

public class UserAndroidAAS extends AndroidAssetAdministrationShell {

    public UserAndroidAAS(IAssetAdministrationShell aas) {
        super(aas);
        setAasType(BdeBaSyxAasTypes.USER_AAS_TYPE);
    }

    public String getUserName(){
        return getDataElementStringValue(getDataElement(
                UserStammDatenSubModel.SUBMODEL_CORE_DATA,
                UserStammDatenSubModel.PROPERTY_USER_NAME));
    }

    public String getName(){
        return getDataElementStringValue(getDataElement(
                UserStammDatenSubModel.SUBMODEL_CORE_DATA,
                UserStammDatenSubModel.PROPERTY_NAME));
    }

    public String getImage(){
        return getDataElementStringValue(getDataElement(
                UserStammDatenSubModel.SUBMODEL_CORE_DATA,
                UserStammDatenSubModel.PROPERTY_IMAGE));
    }

    public List<AndroidDataElement> getRoles(){
        return getSubModelElements(
                UserRoleSubModel.SUBMODEL_ROLES, UserRoleSubModel.PROPERTY_ROLE);
    }

    public List<AndroidDataElement> getSkills(){
        return getSubModelElements(
                UserSkillSubModel.SUBMODEL_SKILLS, UserSkillSubModel.PROPERTY_SKILL);
    }

    public List<AndroidDataElement> getEmails(){
        return getSubModelElements(
                UserStammDatenSubModel.SUBMODEL_CORE_DATA,
                UserStammDatenSubModel.PROPERTY_EMAIL_ADDRESSES);
    }

    public boolean checkUsernameAndPassword(String uName, String password){
        if(uName != null && password != null){
            try {
                AndroidOperation aOp = getOperation(
                        UserStammDatenSubModel.SUBMODEL_CORE_DATA,
                        UserStammDatenSubModel.OPERATION_CHECK_PASSWORD);
                if (aOp != null && aOp.getIOperation() != null) {
                    try {
                        return (Boolean) aOp.getIOperation().invoke(uName, password);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("UserAndroidAAS", "error invoke login: "+e.getMessage());
                    }
                }
            }
            catch (Exception ignored){}
        }

        return false;
    }

    public boolean writeViewData(String id, String data){
        if(data != null){
            try {
                AndroidOperation aOp = getOperation(
                        UserViewSubModel.SUBMODEL_VIEW,
                        UserViewSubModel.OPERATION_ADD_UI_CONFIG);
                if(id == null){
                    AndroidDataElement viewElement = loadDefaultView();
                    id = viewElement != null ? viewElement.getIdShort() : "1";
                }


                if (aOp != null && aOp.getIOperation() != null) {
                    try {
                        return (Boolean) aOp.getIOperation().invoke(id, data);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("UserAndroidAAS", "error invoke login: "+e.getMessage());
                    }
                }
            }
            catch (Exception ignored){}
        }

        return false;
    }


    public String getDefaultViewData(){
        AndroidDataElement viewElement = loadDefaultView();
        return viewElement != null ? viewElement.getValue() : null;
    }

    private AndroidDataElement loadDefaultView(){
        AndroidSubModel smView = getSubModel(UserViewSubModel.SUBMODEL_VIEW);
        if(smView != null){
            AndroidDataElement dataElement = smView.getDataElement(UserViewSubModel.PROPERTY_UI_CONFIG);
            if(dataElement != null && dataElement.getCollectionDataElements().size() > 0){
                // currently use the first element
                return dataElement.getCollectionDataElements().get(0);
            }
        }

        return null;
    }

    public String getViewData(String viewId){
        AndroidDataElement viewElement = loadView(viewId);
        return viewElement != null ? viewElement.getValue() : null;
    }

    private AndroidDataElement loadView(String viewId){
        AndroidSubModel smView = getSubModel(UserViewSubModel.SUBMODEL_VIEW);
        if(smView != null && viewId != null){
            AndroidDataElement dataElement = smView.getDataElement(UserViewSubModel.PROPERTY_UI_CONFIG);
            if(dataElement != null && dataElement.getCollectionDataElements().size() > 0){
                for(AndroidDataElement viewElement : dataElement.getCollectionDataElements()){
                    if(viewElement != null && viewElement.getIdShort() != null &&
                            viewId.compareTo(viewElement.getIdShort()) == 0){
                        return viewElement;
                    }
                }
            }
        }

        return null;
    }
}
