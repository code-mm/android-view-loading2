//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.my.app.view;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.animation.LinearInterpolator;

import java.util.ArrayList;

@SuppressWarnings("all")
public class PacmanIndicator extends Indicator {
    private float translateX;
    private int alpha;
    private float degrees1;
    private float degrees2;

    public PacmanIndicator() {
    }

    public void draw(Canvas canvas, Paint paint) {
        this.drawPacman(canvas, paint);
        this.drawCircle(canvas, paint);
    }

    private void drawPacman(Canvas canvas, Paint paint) {
        float x = (float) (this.getWidth() / 2);
        float y = (float) (this.getHeight() / 2);
        canvas.save();
        canvas.translate(x, y);
        canvas.rotate(this.degrees1);
        paint.setAlpha(255);
        RectF rectF1 = new RectF(-x / 1.7F, -y / 1.7F, x / 1.7F, y / 1.7F);
        canvas.drawArc(rectF1, 0.0F, 270.0F, true, paint);
        canvas.restore();
        canvas.save();
        canvas.translate(x, y);
        canvas.rotate(this.degrees2);
        paint.setAlpha(255);
        RectF rectF2 = new RectF(-x / 1.7F, -y / 1.7F, x / 1.7F, y / 1.7F);
        canvas.drawArc(rectF2, 90.0F, 270.0F, true, paint);
        canvas.restore();
    }

    private void drawCircle(Canvas canvas, Paint paint) {
        float radius = (float) (this.getWidth() / 11);
        paint.setAlpha(this.alpha);
        canvas.drawCircle(this.translateX, (float) (this.getHeight() / 2), radius, paint);
    }

    public ArrayList<ValueAnimator> onCreateAnimators() {
        ArrayList<ValueAnimator> animators = new ArrayList();
        float startT = (float) (this.getWidth() / 11);
        ValueAnimator translationAnim = ValueAnimator.ofFloat(new float[]{(float) this.getWidth() - startT, (float) (this.getWidth() / 2)});
        translationAnim.setDuration(650L);
        translationAnim.setInterpolator(new LinearInterpolator());
        translationAnim.setRepeatCount(-1);
        this.addUpdateListener(translationAnim, new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                PacmanIndicator.this.translateX = (Float) animation.getAnimatedValue();
                PacmanIndicator.this.postInvalidate();
            }
        });
        ValueAnimator alphaAnim = ValueAnimator.ofInt(new int[]{255, 122});
        alphaAnim.setDuration(650L);
        alphaAnim.setRepeatCount(-1);
        this.addUpdateListener(alphaAnim, new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                PacmanIndicator.this.alpha = (Integer) animation.getAnimatedValue();
                PacmanIndicator.this.postInvalidate();
            }
        });
        ValueAnimator rotateAnim1 = ValueAnimator.ofFloat(new float[]{0.0F, 45.0F, 0.0F});
        rotateAnim1.setDuration(650L);
        rotateAnim1.setRepeatCount(-1);
        this.addUpdateListener(rotateAnim1, new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                PacmanIndicator.this.degrees1 = (Float) animation.getAnimatedValue();
                PacmanIndicator.this.postInvalidate();
            }
        });
        ValueAnimator rotateAnim2 = ValueAnimator.ofFloat(new float[]{0.0F, -45.0F, 0.0F});
        rotateAnim2.setDuration(650L);
        rotateAnim2.setRepeatCount(-1);
        this.addUpdateListener(rotateAnim2, new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                PacmanIndicator.this.degrees2 = (Float) animation.getAnimatedValue();
                PacmanIndicator.this.postInvalidate();
            }
        });
        animators.add(translationAnim);
        animators.add(alphaAnim);
        animators.add(rotateAnim1);
        animators.add(rotateAnim2);
        return animators;
    }
}
