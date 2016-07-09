package com.canaan.library;

import android.graphics.Path;

/**
 * Created by mac on 16/7/9.
 */
public interface Shape {

    Path getPath();

    void setWidth(float width);

    void setHeight(float height);

    void setX(float x);

    void setY(float y);

    void setRadius(float radius);

    void setInnerRadius(float innerRadius);

    void setNumOfCorner(int numOfCorner);

    float getWidth();

    float getHeight();

    float getX();

    float getY();

    float getRadius();

    float getInnerRadius();

    int getNumOfCorner();
}

