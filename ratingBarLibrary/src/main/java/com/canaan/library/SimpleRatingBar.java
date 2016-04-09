package com.canaan.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by mac on 16/4/8.
 */
public class SimpleRatingBar extends View {
    private float starWidth;
    private float starHeight;
    private int starSpace;
    private float stepSize;
    private int starNum;
    private float rating;
    private Drawable starDrawable;
    private RatingShape shape;
    private int progressColor;
    private int backgroundColor;
    private boolean isStarClickable;

    private Paint mPaint;
    private Rect starsBoundRect = new Rect();
    private Path shapePath;

    private int mWidth = 100;
    private int mHeight = 20;
    private float right;

    public SimpleRatingBar(Context context) {
        this(context,null,0);
    }

    public SimpleRatingBar(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SimpleRatingBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(attrs);
        initPaint();
        shape = new RatingShape(mPaint);
    }

    private void initAttr(AttributeSet atts) {
        if (atts != null) {
            TypedArray array = getContext().obtainStyledAttributes(atts, R.styleable.SimpleRatingBar);
            starHeight = array.getDimension(R.styleable.SimpleRatingBar_starHeight,10);
            starWidth = array.getDimension(R.styleable.SimpleRatingBar_starWidth,10);
            starSpace = array.getInteger(R.styleable.SimpleRatingBar_starSpace,5);
            stepSize = array.getFloat(R.styleable.SimpleRatingBar_stepSize,1.0f);
            starNum = array.getInteger(R.styleable.SimpleRatingBar_numStar,5);
            rating = array.getFloat(R.styleable.SimpleRatingBar_rating,0);
            progressColor = array.getColor(R.styleable.SimpleRatingBar_starColor, Color.YELLOW);
            backgroundColor = array.getColor(R.styleable.SimpleRatingBar_backgroundColor,Color.GRAY);
            starDrawable = array.getDrawable(R.styleable.SimpleRatingBar_starDrawable);
            isStarClickable = array.getBoolean(R.styleable.SimpleRatingBar_isStarClicable,false);
            array.recycle();
        }
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setColor(backgroundColor);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(1);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int specWidth = MeasureSpec.getSize(widthMeasureSpec);
        int widhtMode = MeasureSpec.getMode(widthMeasureSpec);
        int specHeight = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int boundWidth = getStarsBoundWidth();
        mWidth = boundWidth > mWidth ? boundWidth : mWidth;
        mHeight = starHeight > mHeight ? (int)starHeight : mHeight;

        if (widhtMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(mWidth,mHeight);
        } else if (widhtMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(mWidth,specHeight);
            mHeight = specHeight;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(specWidth,mHeight);
            mWidth = specWidth;
        } else {
            mWidth = getDefaultSize(getSuggestedMinimumWidth(),widthMeasureSpec);
            mHeight = getDefaultSize(getSuggestedMinimumHeight(),heightMeasureSpec);
            setMeasuredDimension(mWidth,mHeight);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int radius = computeRadius();

        int innerRadius = radius / 2;
        float startX = Float.parseFloat(radius * Math.cos(36/180*Math.PI)+"");
        float startY = Float.parseFloat(radius * Math.sin(Math.toRadians(72)) + "");
        getRect(radius,(int)startY);

        canvas.save();
        mPaint.setColor(backgroundColor);
        canvas.clipRect(starsBoundRect, Region.Op.DIFFERENCE);
        for (int i = 0;i < starNum ;i++) {
            canvas.drawPath(shape.setStar(startX,startY,radius,innerRadius,5),mPaint);
            startX += starSpace + radius * 2;
        }
        canvas.restore();
//
        mPaint.setColor(Color.YELLOW);

        canvas.save();
        canvas.clipRect(starsBoundRect);
        startX = Float.parseFloat(radius * Math.cos(36/180*Math.PI)+"");
        for (int i = 0;i < starNum;i++) {
            canvas.drawPath(shape.setStar(startX,startY,radius,innerRadius,5),mPaint);
            startX += starSpace + radius * 2;
        }
        //canvas.drawRect(0,0,1000,1000,mPaint);
        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //Log.d("TAG","getX():"+x+" getLeft():"+getLeft()+" getTransiX():"+getTranslationX());
                //float x = event.getX() - getLeft();
                if (isStarClickable) {
                    right = event.getX();
                    postInvalidate();
                }
        }
        return true;
    }

    private int computeRadius() {
//        if (starWidth > mWidth / 10) {
//            starWidth = mWidth / 10;
//            starHeight = starWidth;
//        }
//        double radius1 = starWidth / (1 + Math.cos(36));
//        double radius2 = starHeight / (2 * Math.sin(72));

//        return (int) Math.min(radius1,radius2);
        return (int)starWidth/2;
    }

    private void getRect(int radius,int startY) {
        //starsBoundRect.set(0,0,getStarsBoundWidth(),(int)starHeight);
        if (right == 0)
            right = Math.round(2 * radius * rating + starSpace * Math.round(rating));
        float bottom = Math.round(2 * startY);
        right = removeReduntant(radius,right);
        starsBoundRect.setEmpty();
        starsBoundRect.set(0,0,Math.round(right),Math.round(bottom));
    }

    private float removeReduntant(int radius,float right) {
        int spaceNum =(int) right / (2 * radius + starSpace);
        float result = Math.round((right - spaceNum * starSpace) / (stepSize * radius * 2)) * (stepSize * radius * 2)
                + spaceNum * starSpace;
        return result;
    }

    private void initShapePath() {
        shapePath = shape.getPath();
    }

    private int getStarsBoundWidth () {
        return (int)starWidth * starNum + starSpace * (starNum - 1);
    }
}
