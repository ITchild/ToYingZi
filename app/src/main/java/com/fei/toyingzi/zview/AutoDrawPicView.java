package com.fei.toyingzi.zview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.fei.toyingzi.R;
import com.fei.toyingzi.util.OpenCvUtil;


/**
 * @author fei
 * @date on 2019/3/5 0005
 * @describe TODO :
 **/
public class AutoDrawPicView extends View {

    private Bitmap drawBitMap;
    private Bitmap cannyBitMap;
    private Bitmap canDrawBitmap;
    private int drawWidth = 0;
    private int drawHight = 0;

    private int screenWidth = 0;
    private int screenHight = 0;

    private RectF subRect;
    private RectF rectF;

    private String showString = "准备好";


    public AutoDrawPicView(Context context) {
        super(context);
        init();
    }

    public AutoDrawPicView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AutoDrawPicView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init(){
        drawBitMap = BitmapFactory.decodeResource(getResources(), R.drawable.p1, null);
        drawWidth = drawBitMap.getWidth();
        drawHight = drawBitMap.getHeight();
        subRect = new RectF(0,0,drawWidth,drawHight);
        rectF = new RectF(0, 0, drawWidth, drawHight);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        screenHight = h;
        screenWidth = w;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        if(null != showString){
            paint.setColor(Color.RED);
            paint.setTextSize(50);
            canvas.drawText(showString,screenWidth/2,screenHight/2,paint);
        }else {
            canvas.drawBitmap(canDrawBitmap , null, rectF, null);
        }
    }

    public void startDrawSub(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                showString = "准备好";
                postInvalidate();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                showString = "3";
                postInvalidate();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                showString = "2";
                postInvalidate();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                showString = "1";
                postInvalidate();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(12);
            }
        }).start();

    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 12) {
                cannyBitMap = OpenCvUtil.getDoneData(drawBitMap);
                showString = null;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for(int i=0;i<15;i++) {
                            canDrawBitmap = i%2==0? drawBitMap : cannyBitMap;
                            postInvalidate();
                            try {
                                Thread.sleep(200);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        if(null !=mOnStartListener){
                            mOnStartListener.start();
                        }
                    }
                }).start();
            }

        }
    };


    public interface OnStartListener{
        void start();
    }

    private OnStartListener mOnStartListener;
    public void setOnStartListener(OnStartListener mOnStartListener){
        this.mOnStartListener = mOnStartListener;
    }

}
