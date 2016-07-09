package com.canaan.library;

import android.graphics.Path;

/**
 * Created by mac on 16/7/9.
 */
public abstract class ShapeDerecotor implements Shape{

    Shape shape;

    public ShapeDerecotor(Shape shape) {
        this.shape = shape;
    }

    public abstract void initDefShape();

    @Override
    public Path getPath() {
        return shape.getPath();
    }

    @Override
    public float getX() {
        return shape.getX();
    }

    @Override
    public float getY() {
        return shape.getY();
    }

    @Override
    public float getRadius() {
        return shape.getRadius();
    }

    @Override
    public float getInnerRadius() {
        return shape.getInnerRadius();
    }

    @Override
    public int getNumOfCorner() {
        return shape.getNumOfCorner();
    }

    public abstract float getInitialX();

    public abstract float getInitialY();
}
