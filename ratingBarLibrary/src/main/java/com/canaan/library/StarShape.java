package com.canaan.library;

import android.graphics.Path;

/**
 * Created by mac on 16/4/8.
 */
public class StarShape implements Shape {

    public float x,y;
    public float width,height;
    public float radius,innerRadius;
    public int numOfCorner;

    private Path setStar( ){
        Path path = new Path();
        double section = 2.0 * Math.PI/numOfCorner;

        path.reset();
        path.moveTo(
                (float)(x + radius * Math.cos(0)),
                (float)(y + radius * Math.sin(0)));
        path.lineTo(
                (float)(x + innerRadius * Math.cos(0 + section/2.0)),
                (float)(y + innerRadius * Math.sin(0 + section/2.0)));

        for(int i=1; i<numOfCorner; i++){
            path.lineTo(
                    (float)(x + radius * Math.cos(section * i)),
                    (float)(y + radius * Math.sin(section * i)));
            path.lineTo(
                    (float)(x + innerRadius * Math.cos(section * i + section/2.0)),
                    (float)(y + innerRadius * Math.sin(section * i + section/2.0)));
        }

        path.close();
        return path;
    }

    public Path getPath(){
        return setStar();
    }

    @Override
    public void setWidth(float width) {
        this.width = width;
    }

    @Override
    public void setHeight(float height) {
        this.height = height;
    }

    @Override
    public void setX(float x) {
        this.x = x;
    }

    @Override
    public void setY(float y) {
        this.y = y;
    }

    @Override
    public void setRadius(float radius) {
        this.radius = radius;
    }

    @Override
    public void setInnerRadius(float innerRadius) {
        this.innerRadius = innerRadius;
    }

    @Override
    public void setNumOfCorner(int numOfCorner) {
        this.numOfCorner = numOfCorner;
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public float getWidth() {
        return width;
    }

    @Override
    public float getHeight() {
        return height;
    }

    @Override
    public float getRadius() {
        return radius;
    }

    @Override
    public float getInnerRadius() {
        return innerRadius;
    }

    @Override
    public int getNumOfCorner() {
        return numOfCorner;
    }
}
