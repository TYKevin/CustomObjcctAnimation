package com.kevin.animator;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView tv_tip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_tip = findViewById(R.id.tv_tip);

        tv_tip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMyObjectAnimator();
            }
        });
    }

    /**
     * 官方初始化方法
     */
    private void startAndroidObjectAnimator() {
        ObjectAnimator objectAnimator = ObjectAnimator
                .ofFloat(tv_tip, "scaleX", 1f, 2f);
        objectAnimator.start();
    }

    /**
     * 初始化一个我们自己写的动画任务
     */
    private void startMyObjectAnimator() {
        MyObjectAnimator objectAnimator = MyObjectAnimator
                .ofFloat(tv_tip, "scaleY", 1f, 2f);
        objectAnimator.setDuration(500);
        objectAnimator.setRepeat(false);
        objectAnimator.setTimeInterpolator(new LineInterpolator());
        objectAnimator.start();
    }
}
