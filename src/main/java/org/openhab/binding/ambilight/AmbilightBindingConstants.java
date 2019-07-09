/**
 * Copyright (c) 2014-2016 by the respective copyright holders.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.ambilight;

import org.eclipse.smarthome.core.thing.ThingTypeUID;

/**
 * The {@link AmbilightBinding} class defines common constants, which are
 * used across the whole binding.
 *
 * @author Wim Vissers - Initial contribution
 */
public class AmbilightBindingConstants {

    public static final String BINDING_ID = "ambilight";

    // List of all Thing Type UIDs
    public final static ThingTypeUID THING_TYPE_TELEVISION = new ThingTypeUID(BINDING_ID, "television");

    // List of all Channel ids
    public final static String CHANNEL_MODE = "mode";
    public final static String CHANNEL_LEFT = "left";
    public final static String CHANNEL_RIGHT = "right";
    public final static String CHANNEL_TOP = "top";
    public final static String CHANNEL_BOTTOM = "bottom";

}
