package de.eyeled.fue.basyx.android.lib.aas.async.callable;

import org.eclipse.basyx.submodel.metamodel.api.ISubmodel;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Callable;

import de.eyeled.fue.basyx.android.lib.aas.AndroidAssetAdministrationShell;
import de.eyeled.fue.basyx.android.lib.aas.AndroidSubModel;

public class ASMCallable extends AndroidSubModel implements Callable<AndroidSubModel>{

    public ASMCallable(@NotNull ISubmodel bSm, @NotNull AndroidAssetAdministrationShell aAas, boolean loadData) {
        super(bSm, aAas, loadData);
    }

    @Override
    public AndroidSubModel call() throws Exception {
        return this;
    }
}
