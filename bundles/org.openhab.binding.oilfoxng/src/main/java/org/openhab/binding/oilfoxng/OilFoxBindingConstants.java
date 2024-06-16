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
package org.openhab.binding.oilfoxng;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.openhab.core.thing.ThingTypeUID;

/**
 * The {@link OilFoxBindingConstants} class defines common constants, which are
 * used across the whole binding.
 *
 * @author Jürgen Seliger - Initial contribution
 */
@NonNullByDefault
public class OilFoxBindingConstants {

    private static final String BINDING_ID = "oilfoxng";

    // List of all Thing Type UIDs
    public final static ThingTypeUID THING_TYPE_BRIDGE = new ThingTypeUID(BINDING_ID, "bridge");
    public final static ThingTypeUID THING_TYPE_OILFOX = new ThingTypeUID(BINDING_ID, "oilfox");

    // List of all Channel ids
    public static final String CHANNEL_VOLUME = "volume";
    public static final String CHANNEL_HEIGHT = "height";
    public static final String CHANNEL_OFFSET = "offset";

    // List of all Channel ids (read-only)
    public static final String CHANNEL_VALUE = "value";
    public static final String CHANNEL_FILLINGPERCENTAGE = "fillingpercentage";
    public static final String CHANNEL_LITERS = "liters";
    public static final String CHANNEL_CURRENTOILHEIGHT = "currentoilheight";
    public static final String CHANNEL_BATTERYLEVEL = "battery-level";

    // List of all supported thing types
    public final static Set<ThingTypeUID> SUPPORTED_DEVICE_TYPES = Collections.singleton(THING_TYPE_OILFOX);
    public final static Set<ThingTypeUID> SUPPORTED_BRIDGE_TYPES = Collections.singleton(THING_TYPE_BRIDGE);
    public final static Set<ThingTypeUID> SUPPORTED_THING_TYPES = Stream.of(THING_TYPE_OILFOX, THING_TYPE_BRIDGE)
            .collect(Collectors.toSet());

    // List of all Properties
    public static final String PROPERTY_VERSION = "version";
    public static final String PROPERTY_OILFOXID = "oilfoxId";
    public static final String PROPERTY_OILFOXHWID = "hardwareId";
    public static final String PROPERTY_ACCESS_TOKEN = "access_token";
    public static final String PROPERTY_REFRESH_TOKEN = "refresh_token";
}
