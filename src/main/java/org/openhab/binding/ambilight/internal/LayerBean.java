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

/**
 * @author Wim Vissers - Initial contribution
 */
public class LayerBean {

    private Map<String, Map<String, RGBBean>> layer1;

    public Map<String, Map<String, RGBBean>> getLayer1() {
        return layer1;
    }

    public void setLayer1(Map<String, Map<String, RGBBean>> layer1) {
        this.layer1 = layer1;
    }

    public class RGBBean {
        private int r;
        private int g;
        private int b;

        public int getR() {
            return r;
        }

        public void setR(int r) {
            this.r = r;
        }

        public int getG() {
            return g;
        }

        public void setG(int g) {
            this.g = g;
        }

        public int getB() {
            return b;
        }

        public void setB(int b) {
            this.b = b;
        }

    }

}
