package org.openhab.binding.ambilight.internal;

/**
 * The Java representation of the topology of the ambilight.
 *
 * layers (integer number): The number of layers.
 * left (integer number): The number of pixels on the left.
 * top (integer number): The number of pixels on the top.
 * right (integer number): The number of pixels on the right.
 * bottom (integer number): The number of pixels on the bottom.
 *
 * @author Wim Vissers
 *
 */
public class TopologyBean {

    private int layers;
    private int left;
    private int top;
    private int bottom;
    private int right;

    public int getLayers() {
        return layers;
    }

    public void setLayers(int layers) {
        this.layers = layers;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public int getBottom() {
        return bottom;
    }

    public void setBottom(int bottom) {
        this.bottom = bottom;
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }

}
