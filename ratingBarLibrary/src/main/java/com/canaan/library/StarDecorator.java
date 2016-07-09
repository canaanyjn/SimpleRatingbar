package com.canaan.library;

/**
 * Created by mac on 16/7/9.
 */
public class StarDecorator extends ShapeDerecotor{

    public StarDecorator(Shape shape) {
        super(shape);
    }

    @Override
    public void initDefShape() {
        computeRadius();
        shape.setInnerRadius(shape.getRadius() / 2);
        shape.setX(Float.parseFloat(shape.getRadius() * Math.cos(36/180*Math.PI)+""));
        shape.setY(Float.parseFloat(shape.getRadius() * Math.sin(Math.toRadians(72)) + ""));
        shape.setNumOfCorner(5);
    }

    @Override
    public void setWidth(float width) {
        shape.setWidth(width);
    }

    @Override
    public void setHeight(float height) {
        shape.setHeight(height);
    }

    @Override
    public void setX(float x) {
        shape.setX(x);
    }

    @Override
    public void setY(float y) {
        shape.setY(y);
    }

    @Override
    public void setRadius(float radius) {
        shape.setRadius(radius);
    }

    @Override
    public void setInnerRadius(float innerRadius) {
        shape.setInnerRadius(innerRadius);
    }

    @Override
    public void setNumOfCorner(int numOfCorner) {
        shape.setNumOfCorner(numOfCorner);
    }

    @Override
    public float getWidth() {
        return shape.getWidth();
    }

    @Override
    public float getHeight() {
        return shape.getHeight();
    }

    private void computeRadius() {
        shape.setRadius(shape.getWidth()/2);
    }

    @Override
    public float getInitialX() {
        return Float.parseFloat(shape.getRadius() * Math.cos(36/180*Math.PI)+"");
    }

    @Override
    public float getInitialY() {
        return Float.parseFloat(shape.getRadius() * Math.cos(36/180*Math.PI)+"");
    }

}
