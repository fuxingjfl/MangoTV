package com.xha.mangotv.util;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.util.Log;

import java.io.IOException;

/**
 *Title:
 *Description:设置返回的照片旋转被纠正的方�?
 *
 *Copyright:Copyright (c) 2016
 *Company:cnTomorrow
 *@author 方爱�?
 *@time 2017/1/10 17:23
 */
public  class RotateBitmap {
    /**
     * 读取图片的旋转的角度
     *
     * @param path
     *            图片绝对路径
     * @return 图片的旋转角�?
     */
    public static int getBitmapDegree(String path) {
        int degree = 0;
        try {
            // 从指定路径下读取图片，并获取其EXIF信息
            ExifInterface exifInterface = new ExifInterface(path);
            // 获取图片的旋转信�?
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }


    /**
     * 将图片按照某个角度进行旋�?
     *
     * @param bm
     *            �?要旋转的图片
     * @param degree
     *            旋转角度
     * @return 旋转后的图片
     */
    public static Bitmap rotateBitmapByDegree(Bitmap bm, int degree) {
        Bitmap returnBm = null;
        if(returnBm!=null){
            returnBm = null;
        }
        // 根据旋转角度，生成旋转矩�?
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        try {
            // 将原始图片按照旋转矩阵进行旋转，并得到新的图�?
            Log.i("returnBm走了吗？",""+returnBm);
            returnBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, false);
            Log.i("returnBm走了吗？",""+returnBm);
        } catch (OutOfMemoryError e) {
        }
        if (returnBm == null) {
            returnBm = bm;
            Log.i("走了吗？",""+bm);
        }
        if (bm != returnBm) {
            bm.recycle();
            Log.i("recycle走了吗？",""+bm);
        }
        return returnBm;
    }
}
