package com.canaan.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class SimpleRatingBar extends View {
    private int starSpace;
    private float stepSize;
    private int starNum;
    private float rating;
    private Drawable starDrawable;
    private int progressColor;
    private int backgroundColor;
    private boolean isStarClickable;

    private Paint mPaint;
    private Rect starsBoundRect = new Rect();
    private ShapeDecorator shapeDecorator;

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

        Shape shape = new StarShape();
        shapeDecorator = new StarDecorator(shape);

        initAttr(attrs);
        initPaint();
    }

    private void initAttr(AttributeSet atts) {
        if (atts != null) {
            TypedArray array = getContext().obtainStyledAttributes(atts, R.styleable.SimpleRatingBar);
            float starHeight = array.getDimension(R.styleable.SimpleRatingBar_starHeight,10);
            float starWidth = array.getDimension(R.styleable.SimpleRatingBar_starWidth,10);
            starSpace = array.getInteger(R.styleable.SimpleRatingBar_starSpace,5);
            stepSize = array.getFloat(R.styleable.SimpleRatingBar_stepSize,1.0f);
            starNum = array.getInteger(R.styleable.SimpleRatingBar_numStar,5);
            rating = array.getFloat(R.styleable.SimpleRatingBar_rating,0);
            progressColor = array.getColor(R.styleable.SimpleRatingBar_starColor, Color.YELLOW);
            backgroundColor = array.getColor(R.styleable.SimpleRatingBar_backgroundColor,Color.GRAY);
            starDrawable = array.getDrawable(R.styleable.SimpleRatingBar_starDrawable);
            isStarClickable = array.getBoolean(R.styleable.SimpleRatingBar_isStarClicable,false);
            array.recycle();

            shapeDecorator.setWidth(starWidth);
            shapeDecorator.setHeight(starHeight);
            shapeDecorator.initDefShape();
        }
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setColor(backgroundColor);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(1);
        mPaint.setStyle(Paint.Style.FILL);
    }

    public ShapeDecorator getShapeDecorator() {
        return shapeDecorator;
    }

    public void setShapeDecorator(ShapeDecorator shapeDecorator) {
        this.shapeDecorator = shapeDecorator;
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
        mHeight = shapeDecorator.getHeight()> mHeight ? (int) shapeDecorator.getHeight() : mHeight;

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

        getRect((int) shapeDecorator.getRadius(), (int) shapeDecorator.getY());

        canvas.save();
        mPaint.setColor(backgroundColor);
        canvas.clipRect(starsBoundRect, Region.Op.DIFFERENCE);
        for (int i = 0;i < starNum ;i++) {
            float startX = shapeDecorator.getX() + starSpace + shapeDecorator.getRadius() * 2;
            shapeDecorator.setX(startX);
            canvas.drawPath(shapeDecorator.getPath(),mPaint);
        }
        canvas.restore();
        mPaint.setColor(progressColor);

        canvas.save();
        canvas.clipRect(starsBoundRect);
        float startX = shapeDecorator.getInitialX();
        for (int i = 0;i < starNum;i++) {
            shapeDecorator.setX(startX);
            canvas.drawPath(shapeDecorator.getPath(),mPaint);
            startX += starSpace + shapeDecorator.getRadius() * 2;
        }
        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (isStarClickable) {
                    right = event.getX();
                    postInvalidate();
                }
        }
        return true;
    }

    private float removeReduntant(int radius,float right) {
        int spaceNum =(int) right / (2 * radius + starSpace);
        float result = Math.round((right - spaceNum * starSpace) / (stepSize * radius * 2)) * (stepSize * radius * 2)
                + spaceNum * starSpace;
        return result;
    }

    private int getStarsBoundWidth () {
        return (int) shapeDecorator.getWidth() * starNum + starSpace * (starNum - 1);
    }

    public void getRect(int radius,int startY) {
        if (right == 0)
            right = Math.round(2 * radius * rating + starSpace * Math.round(rating));
        float bottom = Math.round(2 * startY);
        right = removeReduntant(radius,right);
        starsBoundRect.setEmpty();
        starsBoundRect.set(0,0,Math.round(right),Math.round(bottom));
    }

}
