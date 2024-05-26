package com.ClientServerApp.Model.Coordinates;

import java.io.Serial;
import java.io.Serializable;

public class Coordinates implements Serializable {
    private Double x;
    private Long y; // Field cannot be null. Value must be greater than -507
    @Serial
    private static final long serialVersionUID = 1013L;

    public Coordinates() {}

    public Coordinates(Double x, Long y) {
        this.x = x;
        this.y = y;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Long getY() {
        return y;
    }

    public void setY(Long y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
