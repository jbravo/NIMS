package com.viettel.nims.geo.model.common;

/**
 * Created by SangNV1 on 5/25/2019.
 */
public class BboxForm {
    double maxX;
    double maxY;
    double minX;
    double minY;
    long zoom;
//    List<String> types;

    public double getMaxX() {
        return maxX;
    }

    public void setMaxX(double maxX) {
        this.maxX = maxX;
    }

    public double getMaxY() {
        return maxY;
    }

    public void setMaxY(double maxY) {
        this.maxY = maxY;
    }

    public double getMinX() {
        return minX;
    }

    public void setMinX(double minX) {
        this.minX = minX;
    }

    public double getMinY() {
        return minY;
    }

    public void setMinY(double minY) {
        this.minY = minY;
    }

    public long getZoom() {
        return zoom;
    }

    public void setZoom(long zoom) {
        this.zoom = zoom;
    }

//    public List<String> getTypes() {
//        return types;
//    }

//    public void setTypes(List<String> types) {
//        this.types = types;
//    }
}
