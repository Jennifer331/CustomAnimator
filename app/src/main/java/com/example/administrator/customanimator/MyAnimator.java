package com.example.administrator.customanimator;

import android.graphics.Rect;
import android.util.Log;

/**
 * Created by Lei Xiaoyue on 2015-11-18.
 */
public class MyAnimator {
    private final static String TAG = "MyAnimator";
    private final static float DEFAULT_FACTOR = 1E-1F;
    private final static float SRC_FACTOR = 1E-2F;
    private final static float ERROR = 3E-1F;

    private int mEndX;
    private int mEndY;
    private Rect mEndSrcBound;
    private float mEndScale = 1f;

    private static int i = 0;
    public MyAnimator(int endX, int endY,Rect endSrcBound,float endScale) {
        mEndX = endX;
        mEndY = endY;
        mEndSrcBound = endSrcBound;
        mEndScale = endScale;
    }

    public boolean hasNextFrame(ImageArea object){
        Log.v(TAG,++i + "");
        boolean hasNextFrame = false;
        if(Math.abs(object.getLeft() - mEndX) > ERROR
                || Math.abs(object.getTop() - mEndY) > ERROR
                || Math.abs(object.getScale() - mEndScale) > ERROR
                || Math.abs(object.getSrcBound().left - mEndSrcBound.left) > ERROR
                || Math.abs(object.getSrcBound().right - mEndSrcBound.right) > ERROR
                || Math.abs(object.getSrcBound().top - mEndSrcBound.top) > ERROR
                || Math.abs(object.getSrcBound().bottom - mEndSrcBound.bottom) > ERROR){
            hasNextFrame = true;
            object.setLeft(object.getLeft() + (mEndX - object.getLeft()) * DEFAULT_FACTOR);
            object.setTop(object.getTop() + (mEndY - object.getTop()) * DEFAULT_FACTOR);
            object.setScale(object.getScale() + (mEndScale - object.getScale()) * DEFAULT_FACTOR);
            Rect srcBound = object.getSrcBound();
            if(null != srcBound && null != mEndSrcBound) {
                int left = (int) (srcBound.left + (mEndSrcBound.left - srcBound.left) * SRC_FACTOR);
                int right = (int) (srcBound.right + (mEndSrcBound.right - srcBound.right) * SRC_FACTOR);
                int top = (int) (srcBound.top + (mEndSrcBound.top - srcBound.top) * SRC_FACTOR);
                int bottom = (int) (srcBound.bottom + (mEndSrcBound.bottom - srcBound.bottom) * SRC_FACTOR);
                object.setSrcBound(new Rect(left, top, right, bottom));
            }
        }
        if(!hasNextFrame){

        }
        return hasNextFrame;
    }
}
