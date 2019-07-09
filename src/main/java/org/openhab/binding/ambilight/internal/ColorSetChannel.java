/**
 * Copyright (c) 2010-2018 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.ambilight.internal;

import java.util.Map;

import org.eclipse.smarthome.core.library.types.HSBType;
import org.openhab.binding.ambilight.handler.AmbilightHandler;

/**
 * @author Wim Vissers - Initial contribution
 */
public class ColorSetChannel {

    private AmbilightHandler _owner;
    private ColorSet _colorSet;
    private HSBType _color;

    /**
     * Create ColorSetChannel using LayerBean created from
     * the json string returned by the WebApi.
     *
     * @param owner    the Handler containing the controlled objects.
     * @param colorSet the subset (left, right, top, bottom)
     */
    public ColorSetChannel(AmbilightHandler owner, ColorSet colorSet) {
        _owner = owner;
        _colorSet = colorSet;
    }

    /**
     * Set the color for this pixel set.
     */
    public void setColor(HSBType color) {
        LayerBean layer = _owner.getCached();
        int red = color.getRed().intValue() * 255 / 100;
        int green = color.getGreen().intValue() * 255 / 100;
        int blue = color.getBlue().intValue() * 255 / 100;
        Map<String, LayerBean.RGBBean> map = layer.getLayer1().get(_colorSet._jsonValue);
        for (LayerBean.RGBBean pixel : map.values()) {
            pixel.setR(red);
            pixel.setG(green);
            pixel.setB(blue);
        }
        _owner.setCached(layer, _colorSet, color);
    }

    public HSBType toHSB() {
        return _color;
    }

    public enum ColorSet {
        LEFT("left"),
        RIGHT("right"),
        TOP("top"),
        BOTTOM("bottom");

        private String _jsonValue;

        private ColorSet(String json) {
            _jsonValue = json;
        }

        public String getJsonValue() {
            return _jsonValue;
        }
    }

}
