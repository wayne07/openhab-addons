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
package org.openhab.binding.oilfoxng.internal.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import org.openhab.binding.oilfoxng.OilFoxBindingConstants;
import org.openhab.binding.oilfoxng.internal.handler.OilFoxBridgeHandler;
import org.openhab.binding.oilfoxng.internal.handler.OilFoxStatusListener;
import org.openhab.core.config.discovery.AbstractDiscoveryService;
import org.openhab.core.config.discovery.DiscoveryResult;
import org.openhab.core.config.discovery.DiscoveryResultBuilder;
import org.openhab.core.config.discovery.DiscoveryService;
import org.openhab.core.thing.ThingTypeUID;
import org.openhab.core.thing.ThingUID;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonArray;

/**
 * The {@link OilFoxDiscoveryService} is responsible for discovering the oilfox.
 *
 * @author Jürgen Seliger - Initial contribution
 */
public class OilFoxDiscoveryService extends AbstractDiscoveryService implements OilFoxStatusListener {

    private final Logger logger = LoggerFactory.getLogger(OilFoxDiscoveryService.class);

    private final static int TIMEOUT = 60;

    private ServiceRegistration<?> reg = null;

    private final OilFoxBridgeHandler bridgeHandler;

    public OilFoxDiscoveryService(OilFoxBridgeHandler bridgeHandler) throws IllegalArgumentException {
        super(OilFoxBindingConstants.SUPPORTED_THING_TYPES, TIMEOUT);
        this.bridgeHandler = bridgeHandler;
    }

    public void activate() {
        bridgeHandler.registerOilFoxStatusListener(this);
    }

    @Override
    public void deactivate() {
        bridgeHandler.unregisterOilFoxStatusListener(this);
    }

    @Override
    protected void startScan() {
        try {
            bridgeHandler.summary(false);
        } catch (MalformedURLException e) {
            logger.error("Exception occurred during execution: {}", e.getMessage(), e);
        } catch (IOException e) {
            logger.error("Exception occurred during execution: {}", e.getMessage(), e);
        }
    }

    public void start(BundleContext bundleContext) {
        if (reg != null) {
            return;
        }
        reg = bundleContext.registerService(DiscoveryService.class.getName(), this, new Hashtable<String, Object>());
    }

    public void stop() {
        if (reg != null) {
            reg.unregister();
        }
        reg = null;
    }

    @Override
    public void onOilFoxAdded(ThingUID bridge, String name, String id, String hwid) {
        String n = name;
        if (n == null) {
            n = "Oilfox " + id;
        }

        ThingTypeUID uid = OilFoxBindingConstants.THING_TYPE_OILFOX;
        ThingUID thingUID = new ThingUID(uid, bridge, id);
        logger.trace("Discovered new oilfox {}.", id);

        Map<String, Object> properties = new HashMap<>(3);
        properties.put(OilFoxBindingConstants.PROPERTY_VERSION, "unknown");
        properties.put(OilFoxBindingConstants.PROPERTY_OILFOXID, id);
        properties.put(OilFoxBindingConstants.PROPERTY_OILFOXHWID, hwid);

        DiscoveryResult discoveryResult = DiscoveryResultBuilder.create(thingUID).withLabel(n).withBridge(bridge)
                .withProperties(properties).build();
        thingDiscovered(discoveryResult);
    }

    @Override
    public void onOilFoxRefresh(JsonArray devices) {
    }
}
