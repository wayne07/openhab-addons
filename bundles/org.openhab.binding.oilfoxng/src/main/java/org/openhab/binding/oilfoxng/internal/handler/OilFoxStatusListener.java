/**
 * The {@link OilFoxStatusListener} is notified when an OilFox is added to or removed from the account.
 */
package org.openhab.binding.oilfoxng.internal.handler;

import org.openhab.core.thing.ThingUID;

import com.google.gson.JsonArray;

public interface OilFoxStatusListener {

    /**
     * This method is called whenever an OilFox is added.
     *
     * @param bridge The bridge the added OilFox was connected to.
     * @param name The OilFox which is added.
     * @param id The OilFox which is added.
     * @param hwid The OilFox which is added.
     */
    void onOilFoxAdded(ThingUID bridge, String name, String id, String hwid);

    void onOilFoxRefresh(JsonArray devices);
}
