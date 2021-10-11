package de.eyeled.fue.basyx.android.lib.repository.operations;

import de.eyeled.fue.basyx.android.lib.repository.interfaces.AasRepository;

public interface InvokeOperation {
    Object invoke(AasRepository repository, Object ... objects);
}
