package de.eyeled.fue.basyx.android.lib.aas

import de.eyeled.fue.basyx.android.lib.service.data.BaSyxAasTypes

class RegistryElement {
    var endpoint: String? = null
    var active: Boolean = false

    constructor(endpoint: String?, active: Boolean) {
        this.endpoint = endpoint
        this.active = active
    }

    constructor()

    fun isSame(element: RegistryElement?) : Boolean{
        return element != null && element.endpoint.equals(this.endpoint)
    }

    fun getType() : String {
        if(endpoint != null){
            return endpoint!!
        }

        return BaSyxAasTypes.UNKNOWN_AAS
    }
}