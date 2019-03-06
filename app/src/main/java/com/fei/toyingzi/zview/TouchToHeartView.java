package com.fei.toyingzi.zview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.fei.toyingzi.util.TouchFlake;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fei
 * @date on 2019/3/5 0005
 * @describe TODO :
 **/
public class TouchToHeartView extends View {

    private List<TouchFlake> mTouchFlakes = new ArrayList<>();
    private Paint paint;

    public TouchToHeartView(Context context) {
        super(context);
        init();
    }

    public TouchToHeartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TouchToHeartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(mTouchFlakes.size() != 0) {
            for (TouchFlake touchFlake : mTouchFlakes) {
                touchFlake.draw(canvas);
            }
        }
        getHandler().postDelayed(runnable, 5);
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            invalidate();
        }
    };

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_MOVE){
            mTouchFlakes.add(TouchFlake.create(event.getX(),event.getY(),paint));
            Log.i("fei",event.getX()+"     "+event.getY());
            return true;
        }else if(event.getAction() == MotionEvent.ACTION_DOWN){
            mTouchFlakes.clear();
            return true;
        }
        return super.onTouchEvent(event);
    }
}
