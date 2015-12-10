package com.example.administrator.customanimator;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.Image;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lei Xiaoyue on 2015-11-18.
 */
public class MyView extends View {
    private final static String TAG = "MyView";
    private List<ImageArea> mChildren;
    private GestureDetectorCompat mDetector;

    private static boolean FIRST_IN = true;

    public MyView(Context context) {
        this(context, null);
    }

    public MyView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mChildren = new ArrayList<ImageArea>();
        mDetector = new GestureDetectorCompat(context,new MyGestureListener());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        boolean hasMoreFrame = false;
        if(FIRST_IN){
            loadData();
            FIRST_IN = false;
        }
        if (null == mChildren || 0 == mChildren.size())
            return;
        for (ImageArea child : mChildren) {
            if (null != child) {
                hasMoreFrame |= child.draw(canvas);
            }
        }
        canvas.drawText("i lOVE you", 50f, 50f, new Paint());
        Log.v(TAG, "in on draw,View width:" + getWidth() + " height:" + getHeight());
        if(hasMoreFrame){
            invalidate();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mDetector.onTouchEvent(event);
    }

    private void loadData() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 4;
        Bitmap bitmap = BitmapUtil.decodeWithFillRatioFromResource(getResources(), R.drawable.nature_1,
                500, 500);
        ImageArea item = new ImageArea(1, 50f, 50f, bitmap,new Rect(25,25,100,100));
        Log.v(TAG,item + "");
        if (null != mChildren) {
            mChildren.add(item);
        }
    }

    public class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent event) {
            Toast.makeText(getContext(), "clicked", Toast.LENGTH_SHORT).show();
            ImageArea item = mChildren.get(0);
            Rect endSrcBound = new Rect(0,0,(int)(item.getWidth()),(int)(item.getHeight()));
            float scale = Math.min(event.getY()/item.getWidth(),event.getX()/item.getHeight());
            MyAnimator animator = new MyAnimator(0,0,endSrcBound,scale);
            mChildren.get(0).setAnimator(animator);
            invalidate();
            return true;
        }
    }
}
