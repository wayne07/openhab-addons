/**
 * Copyright (c) 2010-2024 Contributors to the openHAB project
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.openhab.binding.oilfoxng.internal.handler;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.openhab.core.thing.ThingUID;

import com.google.gson.JsonArray;

/**
 * The {@link OilFoxStatusListener} is the interface definition for listening on commands.
 *
 * @author Jürgen Seliger - Initial contribution
 */
@NonNullByDefault
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
