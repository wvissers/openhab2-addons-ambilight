/**
 * Copyright (c) 2010-2018 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.ambilight.internal;

/**
 * ModeBean is the Java representation of the Ambilight mode
 * resource found at /ambilight/mode.
 *
 * Typical json representation: { "current":"manual" }
 *
 * @author Wim Vissers
 *
 */
public class ModeBean {

    private String current;

    public ModeBean() {
    }

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

}
