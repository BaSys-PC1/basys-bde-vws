package de.eyeled.fue.basyx.android.bdevws.lib.aas


class DeviceSetupElement {
    constructor(endpoint: String?, main: Boolean) {
        this.endpoint = endpoint
        this.main = main
    }

    constructor()

    var endpoint: String? = null
    var main: Boolean = false

    fun isSame(element: DeviceSetupElement?) : Boolean{
        return element != null && element.endpoint.equals(this.endpoint)
    }
}