/**
 * Copyright (c) 2010-2017 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.ambilight.internal;

import org.eclipse.smarthome.core.library.types.DecimalType;

/**
 * @author Wim Vissers - Initial contribution
 */
public class ModeChannel {

    private ModeBean _modeBean;

    /**
     * Create ModeChannel using ModeBean created from
     * the json string returned by the WebApi.
     *
     * @param modeBean
     */
    public ModeChannel(ModeBean modeBean) {
        _modeBean = modeBean;
    }

    public ModeChannel(AmbilightMode mode) {
        _modeBean = new ModeBean();
        _modeBean.setCurrent(mode._jsonValue);
    }

    /**
     * Create mode channel from the openHAB state.
     *
     * @param ohState
     */
    public ModeChannel(DecimalType ohState) {
        _modeBean = new ModeBean();
        for (AmbilightMode am : AmbilightMode.values()) {
            if (ohState.intValue() == am._ohState.intValue()) {
                _modeBean.setCurrent(am._jsonValue);
            }
        }
    }

    /**
     * Set the mode of this channel by enum constant.
     *
     * @param mode
     */
    public void setMode(AmbilightMode mode) {
        _modeBean.setCurrent(mode._jsonValue);
    }

    /**
     * Set the mode of this channel by openHAB state (DecimalType).
     *
     * @param mode
     */
    public void setMode(Number ohState) {
        for (AmbilightMode am : AmbilightMode.values()) {
            if (ohState.intValue() == am._ohState.intValue()) {
                _modeBean.setCurrent(am._jsonValue);
            }
        }
    }

    /**
     * Return the current state as AmbilightMode enum.
     *
     * @return
     */
    public AmbilightMode toAmbilightMode() {
        for (AmbilightMode mode : AmbilightMode.values()) {
            if (_modeBean.getCurrent().equals(mode._jsonValue)) {
                return mode;
            }
        }
        return AmbilightMode.UNKNOWN;
    }

    /**
     * Return the current state as ModeBean.
     *
     * @return
     */
    public ModeBean toModeBean() {
        return _modeBean;
    }

    /**
     * Return the current state as DecimalType for openHAB.
     *
     * @return
     */
    public DecimalType toDecimal() {
        return new DecimalType(toAmbilightMode().ordinal());
    }

    /**
     * Enum type with ambilight modes. Mode UNKNOWN is added
     * for unknown responses and should in general be considered
     * as an error.
     *
     * @author wim
     *
     */
    public enum AmbilightMode {
        INTERNAL(0, "internal"),
        MANUAL(1, "manual"),
        EXPERT(2, "expert"),
        UNKNOWN(-1, "unknown");

        private DecimalType _ohState;
        private String _jsonValue;

        private AmbilightMode(int ohValue, String jsonValue) {
            _ohState = new DecimalType(ohValue);
            _jsonValue = jsonValue;
        }
    }

}
