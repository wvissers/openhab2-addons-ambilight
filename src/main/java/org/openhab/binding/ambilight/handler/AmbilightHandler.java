/**
 * Copyright (c) 2014-2016 by the respective copyright holders.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.ambilight.handler;

import static org.openhab.binding.ambilight.AmbilightBindingConstants.*;

import java.math.BigDecimal;

import org.eclipse.smarthome.config.core.Configuration;
import org.eclipse.smarthome.core.library.types.HSBType;
import org.eclipse.smarthome.core.thing.ChannelUID;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingStatus;
import org.eclipse.smarthome.core.thing.ThingStatusDetail;
import org.eclipse.smarthome.core.thing.binding.BaseThingHandler;
import org.eclipse.smarthome.core.types.Command;
import org.eclipse.smarthome.core.types.RefreshType;
import org.openhab.binding.ambilight.internal.ColorSetChannel;
import org.openhab.binding.ambilight.internal.ColorSetChannel.ColorSet;
import org.openhab.binding.ambilight.internal.LayerBean;
import org.openhab.binding.ambilight.internal.ModeChannel;
import org.openhab.binding.ambilight.internal.ModeChannel.AmbilightMode;
import org.openhab.binding.ambilight.internal.RestClient;
import org.openhab.binding.ambilight.internal.TopologyBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link AmbilightHandler} is responsible for handling commands, which are
 * sent to one of the channels.
 *
 * @author Wim Vissers - Initial contribution
 */
public class AmbilightHandler extends BaseThingHandler {

    private Logger _logger = LoggerFactory.getLogger(AmbilightHandler.class);
    private RestClient _restClient;
    private TopologyBean _topology;
    private ModeChannel _modeChannel;
    private ModeChannel _expertMode;
    private ColorSetChannel _left;
    private ColorSetChannel _right;
    private ColorSetChannel _top;
    private ColorSetChannel _bottom;
    private LayerBean _cached;

    public AmbilightHandler(Thing thing) {
        super(thing);
    }

    @Override
    public void handleCommand(ChannelUID channelUID, Command command) {
        if (command instanceof RefreshType) {
            switch (channelUID.getId()) {
                case CHANNEL_MODE:
                    updateState(channelUID, _modeChannel.toDecimal());
                    break;
                case CHANNEL_LEFT:
                    updateState(channelUID, _left.toHSB());
                    break;
                case CHANNEL_RIGHT:
                    updateState(channelUID, _right.toHSB());
                    break;
                case CHANNEL_TOP:
                    updateState(channelUID, _top.toHSB());
                    break;
                case CHANNEL_BOTTOM:
                    updateState(channelUID, _bottom.toHSB());
                    break;
                default:
            }
        } else if (command instanceof HSBType) {
            switch (channelUID.getId()) {
                // Rem: channel state will be updated by the callback to setCached()
                // from the ColorSetChannel instance.
                case CHANNEL_LEFT:
                    _left.setColor((HSBType) command);
                    break;
                case CHANNEL_RIGHT:
                    _right.setColor((HSBType) command);
                    break;
                case CHANNEL_TOP:
                    _top.setColor((HSBType) command);
                    break;
                case CHANNEL_BOTTOM:
                    _bottom.setColor((HSBType) command);
                    break;
                default:
            }
        } else if (command instanceof Number) {
            switch (channelUID.getId()) {
                case CHANNEL_MODE:
                    _restClient.setMode(_expertMode.toModeBean());
                    _modeChannel.setMode((Number) command);
                    _restClient.setMode(_modeChannel.toModeBean());
                    updateState(channelUID, _modeChannel.toDecimal());
                    break;
                default:
            }
        }

    }

    @Override
    public void initialize() {

        Configuration config = getThing().getConfiguration();
        String ip = (String) config.get("ip");
        BigDecimal port = ((BigDecimal) config.get("port"));
        BigDecimal version = ((BigDecimal) config.get("version"));
        _restClient = new RestClient(ip, port.intValue(), version.intValue());

        try {
            _topology = _restClient.getTopology();
            _modeChannel = new ModeChannel(_restClient.getMode());
            _expertMode = new ModeChannel(AmbilightMode.EXPERT);
            _cached = _restClient.getCached();
            _left = new ColorSetChannel(this, ColorSet.LEFT);
            _right = new ColorSetChannel(this, ColorSet.RIGHT);
            _top = new ColorSetChannel(this, ColorSet.TOP);
            _bottom = new ColorSetChannel(this, ColorSet.BOTTOM);
            updateStatus(ThingStatus.ONLINE);
        } catch (Exception ex) {
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.CONFIGURATION_ERROR,
                    "Unable to initialize ambilight, details: " + ex);
        }

        // TODO: Initialize the thing. If done set status to ONLINE to indicate proper working.
        // Long running initialization should be done asynchronously in background.

        // Note: When initialization can NOT be done set the status with more details for further
        // analysis. See also class ThingStatusDetail for all available status details.
        // Add a description to give user information to understand why thing does not work
        // as expected. E.g.
        // updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.CONFIGURATION_ERROR,
        // "Can not access device as username and/or password are invalid");
    }

    public LayerBean getCached() {
        return _cached;
    }

    public void setCached(LayerBean cached, ColorSetChannel.ColorSet colorSet, HSBType color) {
        _cached = cached;
        _restClient.setCached(_cached);
        updateState(new ChannelUID(getThing().getUID(), colorSet.getJsonValue()), color);

    }
}
