package de.eyeled.fue.basyx.android.bdevws.lib.aas;

import org.eclipse.basyx.aas.metamodel.api.IAssetAdministrationShell;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import de.eyeled.fue.basyx.android.bdevws.lib.service.data.BdeBaSyxAasTypes;
import de.eyeled.fue.basyx.android.lib.aas.AndroidAssetAdministrationShell;
import de.eyeled.fue.basyx.android.lib.aas.AndroidDataElement;

public class AnlagenAndroidAAS extends AndroidAssetAdministrationShell {

    public AnlagenAndroidAAS(IAssetAdministrationShell aas) {
        super(aas);
        setAasType(BdeBaSyxAasTypes.ANLAGEN_AAS_TYPE);
    }

    @Override
    public void onSubModelsLoaded() {
        super.onSubModelsLoaded();
    }

    @Nullable
    @Override
    public AndroidDataElement getDataElement(@NotNull String subModelId, @NotNull String dataElementId) {
        return super.getDataElement(subModelId, dataElementId);
    }
}