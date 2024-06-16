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

import org.eclipse.jdt.annotation.NonNull;
import org.openhab.binding.oilfoxng.OilFoxBindingConstants;
import org.openhab.core.thing.Bridge;
import org.openhab.core.thing.ChannelUID;
import org.openhab.core.thing.Thing;
import org.openhab.core.thing.ThingStatus;
import org.openhab.core.thing.ThingStatusDetail;
import org.openhab.core.thing.binding.BaseThingHandler;
import org.openhab.core.types.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link OilFoxHandler} is responsible for handling commands, which are sent to one of the channels.
 *
 * @author Jürgen Seliger - Initial contribution
 */
// public class OilFoxHandler extends BaseThingHandler implements OilFoxStatusListener {
public class OilFoxHandler extends BaseThingHandler {

    private final Logger logger = LoggerFactory.getLogger(OilFoxHandler.class);

    // private @Nullable OilFoxConfiguration config;

    public OilFoxHandler(Thing thing) {
        super(thing);
    }

    @Override
    public void initialize() {
        String oilfoxid = this.getThing().getProperties().get(OilFoxBindingConstants.PROPERTY_OILFOXID);
        if (oilfoxid == null) {
            logger.error("OilFoxId is not set in {}", this.getThing().getUID());
            return;
        }

        Bridge bridge = getBridge();
        if (bridge != null) {
            if (bridge.getStatus() == ThingStatus.ONLINE) {
                logger.debug("initializeThing thing {} bridge status {}", getThing().getUID(), bridge.getStatus());
                // ((OilFoxBridgeHandler) bridge.getHandler()).registerOilFoxStatusListener(this);
                updateStatus(ThingStatus.ONLINE);
            } else {
                updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.BRIDGE_OFFLINE);
            }
        } else {
            updateStatus(ThingStatus.OFFLINE);
        }
    }

    @Override
    public void handleCommand(@NonNull ChannelUID channelUID, Command command) {
    }

    // @Override
    // public void onOilFoxRefresh(JsonArray devices) {
    // String oilfoxid = this.getThing().getProperties().get(OilFoxBindingConstants.PROPERTY_OILFOXID);
    // for (JsonElement device : devices) {
    // if (!device.isJsonObject())
    // continue;
    //
    // JsonObject object = device.getAsJsonObject();
    //
    // String deviceid = object.get("id").getAsString();
    // if (!oilfoxid.equals(deviceid))
    // continue;
    //
    // BigInteger tankHeight = object.get("tankHeight").getAsBigInteger();
    // this.updateState(OilFoxBindingConstants.CHANNEL_HEIGHT, DecimalType.valueOf(tankHeight.toString()));
    // BigInteger tankVolume = object.get("tankVolume").getAsBigInteger();
    // this.updateState(OilFoxBindingConstants.CHANNEL_VOLUME, DecimalType.valueOf(tankVolume.toString()));
    // BigInteger tankOffset = object.get("tankOffset").getAsBigInteger();
    // this.updateState(OilFoxBindingConstants.CHANNEL_OFFSET, DecimalType.valueOf(tankOffset.toString()));
    // // TODO: "tankShape" : "SQUARED"
    // // TODO: "tankIsUsableVolume": false
    // // TODO: "tankUsableVolume": 1000
    // // TODO: "productId": "UUID"
    // // TODO: "notificationInfoEnabled": true,
    // // TODO: "notificationInfoPercentage": 25,
    // // TODO: "notificationAlertEnabled": true,
    // // TODO: "notificationAlertPercentage": 15,
    // // TODO: "measurementIntervalInSeconds": 86400
    //
    // JsonObject metering = object.get("metering").getAsJsonObject();
    // BigDecimal value = metering.get("value").getAsBigDecimal();
    // this.updateState(OilFoxBindingConstants.CHANNEL_VALUE, DecimalType.valueOf(value.toString()));
    // BigDecimal fillingpercentage = metering.get("fillingPercentage").getAsBigDecimal();
    // this.updateState(OilFoxBindingConstants.CHANNEL_FILLINGPERCENTAGE,
    // DecimalType.valueOf(fillingpercentage.toString()));
    // BigDecimal liters = metering.get("liters").getAsBigDecimal();
    // this.updateState(OilFoxBindingConstants.CHANNEL_LITERS, DecimalType.valueOf(liters.toString()));
    // BigDecimal currentOilHeight = metering.get("currentOilHeight").getAsBigDecimal();
    // this.updateState(OilFoxBindingConstants.CHANNEL_CURRENTOILHEIGHT,
    // DecimalType.valueOf(currentOilHeight.toString()));
    // // TODO: "serverDate": 1568035451021
    // BigInteger battery = metering.get("battery").getAsBigInteger();
    // this.updateState(OilFoxBindingConstants.CHANNEL_BATTERYLEVEL, DecimalType.valueOf(battery.toString()));
    //
    // // TODO: "address"
    // // TODO: "partner"
    // // TODO: "chartData"
    // updateStatus(ThingStatus.ONLINE);
    // return;
    // }
    //
    // // Oilfox not found
    // updateStatus(ThingStatus.OFFLINE);
    // }
    //
    // @Override
    // public void onOilFoxAdded(ThingUID bridge, String name, String id, String hwid) {
    // }

    // DEFAULT Implementation
    // @Override
    // public void handleCommand(ChannelUID channelUID, Command command) {
    // if (CHANNEL_1.equals(channelUID.getId())) {
    // if (command instanceof RefreshType) {
    // // TODO: handle data refresh
    // }
    //
    // // TODO: handle command
    //
    // // Note: if communication with thing fails for some reason,
    // // indicate that by setting the status with detail information:
    // // updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR,
    // // "Could not control device at IP address x.x.x.x");
    // }
    // }
    //
    // @Override
    // public void initialize() {
    // config = getConfigAs(OilFoxConfiguration.class);
    //
    // // TODO: Initialize the handler.
    // // The framework requires you to return from this method quickly. Also, before leaving this method a thing
    // // status from one of ONLINE, OFFLINE or UNKNOWN must be set. This might already be the real thing status in
    // // case you can decide it directly.
    // // In case you can not decide the thing status directly (e.g. for long running connection handshake using WAN
    // // access or similar) you should set status UNKNOWN here and then decide the real status asynchronously in the
    // // background.
    //
    // // set the thing status to UNKNOWN temporarily and let the background task decide for the real status.
    // // the framework is then able to reuse the resources from the thing handler initialization.
    // // we set this upfront to reliably check status updates in unit tests.
    // updateStatus(ThingStatus.UNKNOWN);
    //
    // // Example for background initialization:
    // scheduler.execute(() -> {
    // boolean thingReachable = true; // <background task with long running initialization here>
    // // when done do:
    // if (thingReachable) {
    // updateStatus(ThingStatus.ONLINE);
    // } else {
    // updateStatus(ThingStatus.OFFLINE);
    // }
    // });
    //
    // // These logging types should be primarily used by bindings
    // // logger.trace("Example trace message");
    // // logger.debug("Example debug message");
    // // logger.warn("Example warn message");
    //
    // // Note: When initialization can NOT be done set the status with more details for further
    // // analysis. See also class ThingStatusDetail for all available status details.
    // // Add a description to give user information to understand why thing does not work as expected. E.g.
    // // updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.CONFIGURATION_ERROR,
    // // "Can not access device as username and/or password are invalid");
    // }
}
