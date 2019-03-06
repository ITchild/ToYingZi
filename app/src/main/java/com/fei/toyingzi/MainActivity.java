package com.fei.toyingzi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.fei.toyingzi.zview.AutoDrawPicView;
import com.fei.toyingzi.zview.SnowView;
import com.fei.toyingzi.zview.TouchToHeartView;

import org.opencv.android.OpenCVLoader;

public class MainActivity extends AppCompatActivity {

    private AutoDrawPicView main_draw_adpv;
    private SnowView main_snow_sv;
    private TouchToHeartView main_touch_tthv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();
        OpenCVLoader.initDebug();
    }

    private void initView(){
        main_draw_adpv = (AutoDrawPicView) findViewById(R.id.main_draw_adpv);
        main_snow_sv = (SnowView) findViewById(R.id.main_snow_sv);
        main_snow_sv.setVisibility(View.GONE);
        main_touch_tthv = (TouchToHeartView) findViewById(R.id.main_touch_tthv);
        main_touch_tthv.setVisibility(View.GONE);
        main_draw_adpv.startDrawSub();
    }

    private void initListener(){
        main_draw_adpv.setOnStartListener(new AutoDrawPicView.OnStartListener() {
            @Override
            public void start() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        main_snow_sv.setVisibility(View.VISIBLE);
                        main_touch_tthv.setVisibility(View.VISIBLE);
                    }
                });
            }
        });
    }

}
