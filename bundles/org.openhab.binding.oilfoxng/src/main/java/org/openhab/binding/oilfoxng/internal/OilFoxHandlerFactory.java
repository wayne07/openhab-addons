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
package org.openhab.binding.oilfoxng.internal;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.openhab.binding.oilfoxng.OilFoxBindingConstants;
import org.openhab.binding.oilfoxng.internal.handler.OilFoxBridgeHandler;
import org.openhab.binding.oilfoxng.internal.handler.OilFoxHandler;
import org.openhab.binding.oilfoxng.internal.service.OilFoxDiscoveryService;
import org.openhab.core.config.discovery.DiscoveryService;
import org.openhab.core.thing.Bridge;
import org.openhab.core.thing.Thing;
import org.openhab.core.thing.ThingTypeUID;
import org.openhab.core.thing.ThingUID;
import org.openhab.core.thing.binding.BaseThingHandlerFactory;
import org.openhab.core.thing.binding.ThingHandler;
import org.openhab.core.thing.binding.ThingHandlerFactory;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link OilFoxHandlerFactory} is responsible for creating things and thing handlers.
 *
 * @author Jürgen Seliger - Initial contribution
 */
@NonNullByDefault
@Component(configurationPid = "binding.oilfoxng", service = ThingHandlerFactory.class)
public class OilFoxHandlerFactory extends BaseThingHandlerFactory {

    private final Logger logger = LoggerFactory.getLogger(OilFoxHandlerFactory.class);

    private Map<ThingUID, ServiceRegistration<?>> discoveryServiceRegs = new HashMap<>();

    @Override
    public boolean supportsThingType(ThingTypeUID thingTypeUID) {
        return OilFoxBindingConstants.SUPPORTED_THING_TYPES.contains(thingTypeUID);
    }

    @Override
    protected @Nullable ThingHandler createHandler(Thing thing) {
        logger.info("createHandler for {}", thing);
        ThingTypeUID thingTypeUID = thing.getThingTypeUID();
        logger.info("found thingTypeUID {}", thingTypeUID);
        if (OilFoxBindingConstants.SUPPORTED_BRIDGE_TYPES.contains(thingTypeUID)) {
            logger.info("creating Bridge handler");
            OilFoxBridgeHandler bridgeHandler = new OilFoxBridgeHandler((Bridge) thing);
            registerOilFoxDiscoveryService(bridgeHandler);
            return bridgeHandler;
        } else if (thingTypeUID.equals(OilFoxBindingConstants.THING_TYPE_OILFOX)) {
            logger.info("creating OilFox handler");
            return new OilFoxHandler(thing);
        }
        logger.info("creating NO handler");
        return null;
    }

    private synchronized void registerOilFoxDiscoveryService(OilFoxBridgeHandler bridgeHandler) {
        OilFoxDiscoveryService discoveryService = new OilFoxDiscoveryService(bridgeHandler);
        discoveryService.activate();
        this.discoveryServiceRegs.put(bridgeHandler.getThing().getUID(),
                bundleContext.registerService(DiscoveryService.class.getName(), discoveryService, new Hashtable<>()));
    }
}
