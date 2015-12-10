package com.example.administrator.customanimator;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

/**
 * Created by Lei Xiaoyue on 2015-11-10.
 */
public class BitmapUtil {
    private static final String TAG = "BitmapUtil";

    public static Bitmap decodeBitmapFromFile(String pathName, int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathName, options);

        options.inJustDecodeBounds = false;
        options.inSampleSize = calculateInSampleSize2(options, reqWidth, reqHeight);
        Log.v(TAG, "Decode Sample" + options.inSampleSize + "");
        return BitmapFactory.decodeFile(pathName, options);
    }

    public static Bitmap decodeBitmapFromResource(Resources resources,int id, int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resources, id, options);

        options.inJustDecodeBounds = false;
        options.inSampleSize = calculateInSampleSize2(options, reqWidth, reqHeight);
        Log.v(TAG, "Decode Sample" + options.inSampleSize + "");
        return BitmapFactory.decodeResource(resources, id, options);
    }

    private static Bitmap decodeWithFillRatio(Bitmap bitmap,int destWidh,int destHeight){
        float suggestedScale = 1f;
        float alignTranslateX = 0f;
        float alignTranslateY = 0f;
        float bitmapRatio = (float) bitmap.getWidth() / (float) bitmap.getHeight();
        float destinationRatio = (float) destWidh / (float) destHeight;
        if (bitmapRatio < destinationRatio) {
            suggestedScale = (float) destWidh / (float) bitmap.getWidth();
            alignTranslateX = 0f;
            alignTranslateY = -(bitmap.getHeight() * suggestedScale - destHeight) / 2;
            if (alignTranslateY < 0) {
                alignTranslateY = 0;
            }
        } else {
            suggestedScale = (float) destHeight / (float) bitmap.getHeight();
            alignTranslateX = -(bitmap.getWidth() * suggestedScale - destWidh) / 2;
            alignTranslateY = 0f;
            if (alignTranslateX < 0) {
                alignTranslateX = 0;
            }
        }
        Bitmap resultBitmap = Bitmap.createBitmap(destWidh, destHeight, Bitmap.Config.ARGB_8888);
        Canvas thumbCanvas = new Canvas(resultBitmap);
        thumbCanvas.scale(suggestedScale, suggestedScale);
        thumbCanvas.translate(alignTranslateX, alignTranslateY);
        thumbCanvas.drawBitmap(bitmap, 0, 0, null);

        return resultBitmap;
    }

    private static Bitmap decodeWithFullRatio(Bitmap bitmap,int destWidth,int destHeight){
        float suggestedScale = 1f;
        float alignTranslateX = 0f;
        float alignTranslateY = 0f;
        float bitmapRatio = (float) bitmap.getWidth() / (float) bitmap.getHeight();
        float destinationRatio = (float) destWidth / (float) destHeight;
        if (bitmapRatio > destinationRatio) {
            suggestedScale = (float) destWidth / (float) bitmap.getWidth();
            alignTranslateX = 0f;
            alignTranslateY = -(bitmap.getHeight() * suggestedScale - destHeight) / 2;
            if (alignTranslateY < 0) {
                alignTranslateY = 0;
            }
        } else {
            suggestedScale = (float) destHeight / (float) bitmap.getHeight();
            alignTranslateX = -(bitmap.getWidth() * suggestedScale - destWidth) / 2;
            alignTranslateY = 0f;
            if (alignTranslateX < 0) {
                alignTranslateX = 0;
            }
        }
        Bitmap resultBitmap = Bitmap.createBitmap(destWidth, destHeight, Bitmap.Config.ARGB_8888);
        Canvas thumbCanvas = new Canvas(resultBitmap);
        thumbCanvas.scale(suggestedScale, suggestedScale);
        thumbCanvas.translate(alignTranslateX, alignTranslateY);
        thumbCanvas.drawBitmap(bitmap, 0, 0, null);

        return resultBitmap;
    }

    public static Bitmap decodeWithFillRatioFromFile(String path,int destWidth,int destHeight){
        Bitmap bitmap = decodeBitmapFromFile(path,destWidth,destHeight);
        return decodeWithFillRatio(bitmap,destWidth,destHeight);
    }

    public static Bitmap decodeWithFullRatioFromFile(String path,int destWidth,int destHeight){
        Bitmap bitmap = decodeBitmapFromFile(path,destWidth,destHeight);
        return decodeWithFullRatio(bitmap,destWidth,destHeight);
    }

    public static Bitmap decodeWithFillRatioFromResource(Resources resources,int id,int destWidh,int destHeight){
        Bitmap bitmap = decodeBitmapFromResource(resources,id,destWidh,destHeight);
        return decodeWithFillRatio(bitmap,destWidh,destHeight);
    }

    public static Bitmap decodeWithFullRatioFromResource(Resources resources,int id,int destWidh,int destHeight){
        Bitmap bitmap = decodeBitmapFromResource(resources,id,destWidh,destHeight);
        return decodeWithFullRatio(bitmap,destWidh,destHeight);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth,
            int reqHeight) {
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height >> 1;
            final int halfWeight = width / 2;

            while (halfHeight / inSampleSize > reqHeight && halfWeight / inSampleSize > reqWidth) {
                inSampleSize *= 2;
            }
        }

        long totalPixels = height * width / inSampleSize;
        final long reqTotalPixelsCap = reqHeight * reqWidth * 2;
        while (totalPixels > reqTotalPixelsCap) {
            totalPixels *= 0.5;
            inSampleSize *= 2;
        }
        return inSampleSize;
    }

    public static int calculateInSampleSize2(BitmapFactory.Options options, int reqWidth,
            int reqHeight) {
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;

        if (height != 0 && reqHeight != 0) {
            if (width / height > reqWidth / reqHeight)
                inSampleSize = width / reqWidth;
            else
                inSampleSize = height / reqHeight;
        }
        return lastPowerOf2(inSampleSize * inSampleSize);
    }

    public static int lastPowerOf2(final int a) {
        int b = 1;
        while (b < a) {
            b = b << 1;
        }
        return b == 1 ? 1 : b >> 1;
    }

    public static Bitmap pileUpBitmaps(Bitmap bitmaps[], int destWidth, int destHeight) {
        Bitmap newBitmap = Bitmap.createBitmap(destWidth, destHeight, Bitmap.Config.ARGB_8888);
        int size = bitmaps.length;
        if (bitmaps.length > 0) {
            Canvas canvas = new Canvas(newBitmap);
            canvas.save(Canvas.ALL_SAVE_FLAG);
            Paint paint = new Paint();
            canvas.translate(30 * (size - 1), 0);
            for (int i = 0; i < bitmaps.length; i++) {
                // canvas.drawBitmap(bitmaps[i], 0, 0, paint);
                canvas.drawBitmap(Bitmap.createScaledBitmap(bitmaps[i], destWidth - 30 * (size - 1),
                        destHeight - 30 * (size - 1), false), 0, 0, paint);
                canvas.translate(-30, 30);
            }
            canvas.restore();
        }

        return newBitmap;
    }
}
