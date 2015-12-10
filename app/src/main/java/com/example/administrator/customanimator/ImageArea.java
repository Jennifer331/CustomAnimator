package com.example.administrator.customanimator;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by Lei Xiaoyue on 2015-11-17.
 */
public class ImageArea {
    private int mPosition;
    private float mLeft;
    private float mTop;
    private Bitmap mSrc;
    private Rect mSrcBound;
    private float mScale = 1f;
    private MyAnimator mAnimator;

    public ImageArea(int position, float x, float y, Bitmap src,Rect srcBound) {
        mPosition = position;
        mLeft = x;
        mTop = y;
        mSrc = src;
        mSrcBound = srcBound;
    }

    public boolean draw(Canvas canvas) {
        if (null != mSrc) {
            {
                canvas.save();
                Rect dest = new Rect((int) mLeft, (int) mTop, (int) (mLeft + getWidth()),
                        (int) (mTop + getHeight()));
                canvas.scale(mScale, mScale);
                canvas.drawBitmap(mSrc, mSrcBound, dest, null);
                canvas.restore();
            }
            if (null != mAnimator) {
                while (mAnimator.hasNextFrame(this)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "mPosition:" + mPosition + " X:" + mLeft + " Y:" + mTop + " width:" + getWidth()
                + " height:" + getHeight() + " src " + (null == mSrc ? "is " : "is not") + " null";
    }

    public void setAnimator(MyAnimator Animator) {
        this.mAnimator = Animator;
    }

    public int getPosition() {
        return mPosition;
    }

    public void setPosition(int mPosition) {
        this.mPosition = mPosition;
    }

    public float getLeft() {
        return mLeft;
    }

    public void setLeft(float x) {
        this.mLeft = x;
    }

    public float getTop() {
        return mTop;
    }

    public void setTop(float y) {
        this.mTop = y;
    }

    public float getWidth() {
        return mSrc.getWidth();
    }

    public float getHeight() {
        return mSrc.getHeight();
    }

    public Bitmap getSrc() {
        return mSrc;
    }

    public void setSrc(Bitmap src) {
        this.mSrc = src;
    }

    public Rect getSrcBound() {
        return mSrcBound;
    }

    public void setSrcBound(Rect mSrcBound) {
        this.mSrcBound = mSrcBound;
    }

    public float getScale() {
        return mScale;
    }

    public void setScale(float mScale) {
        this.mScale = mScale;
    }
}
