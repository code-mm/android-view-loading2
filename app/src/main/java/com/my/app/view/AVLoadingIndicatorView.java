//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.my.app.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.Callback;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;

import com.my.app.R;

@SuppressWarnings("all")
public class AVLoadingIndicatorView extends View {
    private static final String TAG = "AVLoadingIndicatorView";
    private static final PacmanIndicator DEFAULT_INDICATOR = new PacmanIndicator();
    private static final int MIN_SHOW_TIME = 500;
    private static final int MIN_DELAY = 500;
    private long mStartTime = -1L;
    private boolean mPostedHide = false;
    private boolean mPostedShow = false;
    private boolean mDismissed = false;
    private final Runnable mDelayedHide;
    private final Runnable mDelayedShow;
    int mMinWidth;
    int mMaxWidth;
    int mMinHeight;
    int mMaxHeight;
    private Indicator mIndicator;
    private int mIndicatorColor;
    private boolean mShouldStartAnimationDrawable;

    public AVLoadingIndicatorView(Context context) {
        super(context);
        this.mDelayedHide = new NamelessClass_2();
        this.mDelayedShow = new NamelessClass_1();
        this.init(context, (AttributeSet) null, 0, 0);
    }

    public AVLoadingIndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mDelayedHide = new NamelessClass_2();
        this.mDelayedShow = new NamelessClass_1();
        this.init(context, attrs, 0, R.style.AVLoadingIndicatorView);
    }

    public AVLoadingIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mDelayedHide = new NamelessClass_2();
        this.mDelayedShow = new NamelessClass_1();
        this.init(context, attrs, defStyleAttr, R.style.AVLoadingIndicatorView);
    }

    @TargetApi(21)
    public AVLoadingIndicatorView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mDelayedHide = new NamelessClass_2();
        this.mDelayedShow = new NamelessClass_1();
        this.init(context, attrs, defStyleAttr, R.style.AVLoadingIndicatorView);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        this.mMinWidth = 24;
        this.mMaxWidth = 48;
        this.mMinHeight = 24;
        this.mMaxHeight = 48;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AVLoadingIndicatorView, defStyleAttr, defStyleRes);
        this.mMinWidth = a.getDimensionPixelSize(R.styleable.AVLoadingIndicatorView_minWidth, this.mMinWidth);
        this.mMaxWidth = a.getDimensionPixelSize(R.styleable.AVLoadingIndicatorView_maxWidth, this.mMaxWidth);
        this.mMinHeight = a.getDimensionPixelSize(R.styleable.AVLoadingIndicatorView_minHeight, this.mMinHeight);
        this.mMaxHeight = a.getDimensionPixelSize(R.styleable.AVLoadingIndicatorView_maxHeight, this.mMaxHeight);
        String indicatorName = a.getString(R.styleable.AVLoadingIndicatorView_indicatorName);
        this.mIndicatorColor = a.getColor(R.styleable.AVLoadingIndicatorView_indicatorColor, -1);
        this.setIndicator(indicatorName);
        if (this.mIndicator == null) {
            this.setIndicator((Indicator) DEFAULT_INDICATOR);
        }

        a.recycle();
    }

    public Indicator getIndicator() {
        return this.mIndicator;
    }

    public void setIndicator(Indicator d) {
        if (this.mIndicator != d) {
            if (this.mIndicator != null) {
                this.mIndicator.setCallback((Callback) null);
                this.unscheduleDrawable(this.mIndicator);
            }

            this.mIndicator = d;
            this.setIndicatorColor(this.mIndicatorColor);
            if (d != null) {
                d.setCallback(this);
            }

            this.postInvalidate();
        }

    }

    public void setIndicatorColor(int color) {
        this.mIndicatorColor = color;
        this.mIndicator.setColor(color);
    }

    public void setIndicator(String indicatorName) {
        if (!TextUtils.isEmpty(indicatorName)) {
            try {
                Class<?> drawableClass = Class.forName(indicatorName);
                Indicator indicator = (Indicator) drawableClass.newInstance();
                this.setIndicator(indicator);
            } catch (ClassNotFoundException var4) {
                Log.e("AVLoadingIndicatorView", "Didn't find your class , check the name again !");
            } catch (InstantiationException var5) {
                var5.printStackTrace();
            } catch (IllegalAccessException var6) {
                var6.printStackTrace();
            }

        }
    }

    public void smoothToShow() {
        this.startAnimation(AnimationUtils.loadAnimation(this.getContext(), 17432576));
        this.setVisibility(0);
    }

    public void smoothToHide() {
        this.startAnimation(AnimationUtils.loadAnimation(this.getContext(), 17432577));
        this.setVisibility(8);
    }

    public void hide() {
        this.mDismissed = true;
        this.removeCallbacks(this.mDelayedShow);
        long diff = System.currentTimeMillis() - this.mStartTime;
        if (diff < 500L && this.mStartTime != -1L) {
            if (!this.mPostedHide) {
                this.postDelayed(this.mDelayedHide, 500L - diff);
                this.mPostedHide = true;
            }
        } else {
            this.setVisibility(8);
        }

    }

    public void show() {
        this.mStartTime = -1L;
        this.mDismissed = false;
        this.removeCallbacks(this.mDelayedHide);
        if (!this.mPostedShow) {
            this.postDelayed(this.mDelayedShow, 500L);
            this.mPostedShow = true;
        }

    }

    protected boolean verifyDrawable(Drawable who) {
        return who == this.mIndicator || super.verifyDrawable(who);
    }

    void startAnimation() {
        if (this.getVisibility() == 0) {
            if (this.mIndicator instanceof Animatable) {
                this.mShouldStartAnimationDrawable = true;
            }

            this.postInvalidate();
        }
    }

    void stopAnimation() {
        if (this.mIndicator instanceof Animatable) {
            this.mIndicator.stop();
            this.mShouldStartAnimationDrawable = false;
        }

        this.postInvalidate();
    }

    public void setVisibility(int v) {
        if (this.getVisibility() != v) {
            super.setVisibility(v);
            if (v != 8 && v != 4) {
                this.startAnimation();
            } else {
                this.stopAnimation();
            }
        }

    }

    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (visibility != 8 && visibility != 4) {
            this.startAnimation();
        } else {
            this.stopAnimation();
        }

    }

    public void invalidateDrawable(Drawable dr) {
        if (this.verifyDrawable(dr)) {
            Rect dirty = dr.getBounds();
            int scrollX = this.getScrollX() + this.getPaddingLeft();
            int scrollY = this.getScrollY() + this.getPaddingTop();
            this.invalidate(dirty.left + scrollX, dirty.top + scrollY, dirty.right + scrollX, dirty.bottom + scrollY);
        } else {
            super.invalidateDrawable(dr);
        }

    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        this.updateDrawableBounds(w, h);
    }

    private void updateDrawableBounds(int w, int h) {
        w -= this.getPaddingRight() + this.getPaddingLeft();
        h -= this.getPaddingTop() + this.getPaddingBottom();
        int right = w;
        int bottom = h;
        int top = 0;
        int left = 0;
        if (this.mIndicator != null) {
            int intrinsicWidth = this.mIndicator.getIntrinsicWidth();
            int intrinsicHeight = this.mIndicator.getIntrinsicHeight();
            float intrinsicAspect = (float) intrinsicWidth / (float) intrinsicHeight;
            float boundAspect = (float) w / (float) h;
            if (intrinsicAspect != boundAspect) {
                int width;
                if (boundAspect > intrinsicAspect) {
                    width = (int) ((float) h * intrinsicAspect);
                    left = (w - width) / 2;
                    right = left + width;
                } else {
                    width = (int) ((float) w * (1.0F / intrinsicAspect));
                    top = (h - width) / 2;
                    bottom = top + width;
                }
            }

            this.mIndicator.setBounds(left, top, right, bottom);
        }

    }

    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.drawTrack(canvas);
    }

    void drawTrack(Canvas canvas) {
        Drawable d = this.mIndicator;
        if (d != null) {
            int saveCount = canvas.save();
            canvas.translate((float) this.getPaddingLeft(), (float) this.getPaddingTop());
            d.draw(canvas);
            canvas.restoreToCount(saveCount);
            if (this.mShouldStartAnimationDrawable && d instanceof Animatable) {
                ((Animatable) d).start();
                this.mShouldStartAnimationDrawable = false;
            }
        }

    }

    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int dw = 0;
        int dh = 0;
        Drawable d = this.mIndicator;
        if (d != null) {
            dw = Math.max(this.mMinWidth, Math.min(this.mMaxWidth, d.getIntrinsicWidth()));
            dh = Math.max(this.mMinHeight, Math.min(this.mMaxHeight, d.getIntrinsicHeight()));
        }

        this.updateDrawableState();
        dw += this.getPaddingLeft() + this.getPaddingRight();
        dh += this.getPaddingTop() + this.getPaddingBottom();
        int measuredWidth = resolveSizeAndState(dw, widthMeasureSpec, 0);
        int measuredHeight = resolveSizeAndState(dh, heightMeasureSpec, 0);
        this.setMeasuredDimension(measuredWidth, measuredHeight);
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        this.updateDrawableState();
    }

    private void updateDrawableState() {
        int[] state = this.getDrawableState();
        if (this.mIndicator != null && this.mIndicator.isStateful()) {
            this.mIndicator.setState(state);
        }

    }

    @TargetApi(21)
    public void drawableHotspotChanged(float x, float y) {
        super.drawableHotspotChanged(x, y);
        if (this.mIndicator != null) {
            this.mIndicator.setHotspot(x, y);
        }

    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.startAnimation();
        this.removeCallbacks();
    }

    protected void onDetachedFromWindow() {
        this.stopAnimation();
        super.onDetachedFromWindow();
        this.removeCallbacks();
    }

    private void removeCallbacks() {
        this.removeCallbacks(this.mDelayedHide);
        this.removeCallbacks(this.mDelayedShow);
    }

    class NamelessClass_1 implements Runnable {
        NamelessClass_1() {
        }

        public void run() {
            AVLoadingIndicatorView.this.mPostedShow = false;
            if (!AVLoadingIndicatorView.this.mDismissed) {
                AVLoadingIndicatorView.this.mStartTime = System.currentTimeMillis();
                AVLoadingIndicatorView.this.setVisibility(0);
            }

        }
    }

    class NamelessClass_2 implements Runnable {
        NamelessClass_2() {
        }

        public void run() {
            AVLoadingIndicatorView.this.mPostedHide = false;
            AVLoadingIndicatorView.this.mStartTime = -1L;
            AVLoadingIndicatorView.this.setVisibility(8);
        }
    }
}
