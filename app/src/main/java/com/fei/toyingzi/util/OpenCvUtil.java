package com.fei.toyingzi.util;

import android.graphics.Bitmap;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

/**
 * @author fei
 * @date on 2019/3/5 0005
 * @describe TODO :
 **/
public class OpenCvUtil {

    public static Bitmap getDoneData(Bitmap bitmap){
        Bitmap doneBitmap = null;
        Mat mat = new Mat();
        org.opencv.android.Utils.bitmapToMat(bitmap, mat);
        mat = grayImage(mat);
        Mat cannyMat = cannyEdge(mat);
        if (null == doneBitmap) {
            doneBitmap = Bitmap.createBitmap(mat.cols(), mat.rows(), Bitmap.Config.ARGB_8888);
        }
        org.opencv.android.Utils.matToBitmap(cannyMat, doneBitmap);
        return doneBitmap;
    }



    //灰度处理
    public static Mat grayImage(Mat srcMat) {
        Mat grayImg = new Mat();
        Imgproc.cvtColor(srcMat, grayImg, Imgproc.COLOR_RGB2GRAY);
        return grayImg;
    }

    //canny边缘检测
    public static Mat cannyEdge(Mat srcMat) {
        final Mat edgeImage = new Mat(); //100低阈值 200：高阈值
        Imgproc.Canny(srcMat, edgeImage, 70, 70, 3, true);
        return edgeImage;
    }
}
