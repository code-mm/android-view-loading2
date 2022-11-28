//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.my.app.view;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
@SuppressWarnings("all")
public abstract class Indicator extends Drawable implements Animatable {
    private HashMap<ValueAnimator, AnimatorUpdateListener> mUpdateListeners = new HashMap();
    private ArrayList<ValueAnimator> mAnimators;
    private int alpha = 255;
    private static final Rect ZERO_BOUNDS_RECT = new Rect();
    protected Rect drawBounds;
    private boolean mHasAnimators;
    private Paint mPaint;

    public Indicator() {
        this.drawBounds = ZERO_BOUNDS_RECT;
        this.mPaint = new Paint();
        this.mPaint.setColor(-1);
        this.mPaint.setStyle(Style.FILL);
        this.mPaint.setAntiAlias(true);
    }

    public int getColor() {
        return this.mPaint.getColor();
    }

    public void setColor(int color) {
        this.mPaint.setColor(color);
    }

    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }

    public int getAlpha() {
        return this.alpha;
    }

    public int getOpacity() {
        return -1;
    }

    public void setColorFilter(ColorFilter colorFilter) {
    }

    public void draw(Canvas canvas) {
        this.draw(canvas, this.mPaint);
    }

    public abstract void draw(Canvas var1, Paint var2);

    public abstract ArrayList<ValueAnimator> onCreateAnimators();

    public void start() {
        this.ensureAnimators();
        if (this.mAnimators != null) {
            if (!this.isStarted()) {
                this.startAnimators();
                this.invalidateSelf();
            }
        }
    }

    private void startAnimators() {
        for(int i = 0; i < this.mAnimators.size(); ++i) {
            ValueAnimator animator = (ValueAnimator)this.mAnimators.get(i);
            AnimatorUpdateListener updateListener = (AnimatorUpdateListener)this.mUpdateListeners.get(animator);
            if (updateListener != null) {
                animator.addUpdateListener(updateListener);
            }

            animator.start();
        }

    }

    private void stopAnimators() {
        if (this.mAnimators != null) {
            Iterator var1 = this.mAnimators.iterator();

            while(var1.hasNext()) {
                ValueAnimator animator = (ValueAnimator)var1.next();
                if (animator != null && animator.isStarted()) {
                    animator.removeAllUpdateListeners();
                    animator.end();
                }
            }
        }

    }

    private void ensureAnimators() {
        if (!this.mHasAnimators) {
            this.mAnimators = this.onCreateAnimators();
            this.mHasAnimators = true;
        }

    }

    public void stop() {
        this.stopAnimators();
    }

    private boolean isStarted() {
        Iterator var1 = this.mAnimators.iterator();
        if (var1.hasNext()) {
            ValueAnimator animator = (ValueAnimator)var1.next();
            return animator.isStarted();
        } else {
            return false;
        }
    }

    public boolean isRunning() {
        Iterator var1 = this.mAnimators.iterator();
        if (var1.hasNext()) {
            ValueAnimator animator = (ValueAnimator)var1.next();
            return animator.isRunning();
        } else {
            return false;
        }
    }

    public void addUpdateListener(ValueAnimator animator, AnimatorUpdateListener updateListener) {
        this.mUpdateListeners.put(animator, updateListener);
    }

    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        this.setDrawBounds(bounds);
    }

    public void setDrawBounds(Rect drawBounds) {
        this.setDrawBounds(drawBounds.left, drawBounds.top, drawBounds.right, drawBounds.bottom);
    }

    public void setDrawBounds(int left, int top, int right, int bottom) {
        this.drawBounds = new Rect(left, top, right, bottom);
    }

    public void postInvalidate() {
        this.invalidateSelf();
    }

    public Rect getDrawBounds() {
        return this.drawBounds;
    }

    public int getWidth() {
        return this.drawBounds.width();
    }

    public int getHeight() {
        return this.drawBounds.height();
    }

    public int centerX() {
        return this.drawBounds.centerX();
    }

    public int centerY() {
        return this.drawBounds.centerY();
    }

    public float exactCenterX() {
        return this.drawBounds.exactCenterX();
    }

    public float exactCenterY() {
        return this.drawBounds.exactCenterY();
    }
}
