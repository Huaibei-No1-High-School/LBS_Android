package com.hoshizora.lbs;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.Button;

public class WaveButton extends Button {

    private static final int invalidateDuration = 15;
    private static int diffuseRadioIncrement = 10;
    private static int tapTimeout;
    private int viewWidth, viewHeight;
    private int pointX, pointY;
    private int maxRadio;
    private int diffuseRadio;
    private Paint bottomPaint, colorPaint;
    private boolean isPressed;
    private int eventX, eventY;
    private long downTime = 0;

    private void initPaint() {
        colorPaint = new Paint();
        bottomPaint = new Paint();
        int bgcolor = ((ColorDrawable)this.getBackground()).getColor();
        colorPaint.setColor(getResources().getColor(R.color.light_green_2));
        bottomPaint.setColor(bgcolor);
    }

    public WaveButton(Context context, AttributeSet attributeSet) {
        super(context,attributeSet);
        initPaint();
        tapTimeout = ViewConfiguration.getLongPressTimeout();
    }

    private void calculateMaxRadio() {
        if (eventX > viewHeight) {
            if (eventX < viewWidth / 2) {
                maxRadio = viewWidth - eventX;
            }
            else {
                maxRadio = viewWidth / 2 + eventX;
            }
        }
        else {
            if (eventY < viewHeight / 2) {
                maxRadio = viewHeight - eventY;
            }
            else {
                maxRadio = viewHeight / 2 + eventY;
            }
        }
    }

    private void clearData() {
        downTime = 0;
        diffuseRadioIncrement = 10;
        isPressed = false;
        diffuseRadio = 0;
        postInvalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (downTime == 0) downTime = SystemClock.elapsedRealtime();
                eventX = (int)event.getX();
                eventY = (int)event.getY();
                calculateMaxRadio();
                isPressed = true;
                postInvalidateDelayed(invalidateDuration);
                break;
            case MotionEvent.ACTION_CANCEL:
                if (SystemClock.elapsedRealtime() - downTime < tapTimeout) {
                    diffuseRadioIncrement = 30;
                    postInvalidate();
                }
                else {
                    clearData();
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (!isPressed) {
            super.onDraw(canvas);
            return;
        }
        canvas.drawRect(pointX, pointY, pointX + viewWidth, pointY + viewHeight, bottomPaint);
        canvas.save();
        canvas.clipRect(pointX, pointY, pointX + viewWidth, pointY + viewHeight);
        canvas.drawCircle(eventX, eventY, diffuseRadio, colorPaint);
        canvas.restore();
        super.onDraw(canvas);
        if (diffuseRadio < maxRadio) {
            postInvalidateDelayed(invalidateDuration, pointX, pointY, pointX + viewWidth, pointY + viewHeight);
            diffuseRadio += diffuseRadioIncrement;
        }
        else {
            clearData();
        }
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        super.onSizeChanged(width, height, oldWidth, oldHeight);
        this.viewWidth = width;
        this.viewHeight = height;
    }
}
