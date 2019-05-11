package com.kevin.animator;

import java.lang.ref.WeakReference;

/**
 * 动画任务
 */
public class MyObjectAnimator implements VSYNCManager.AnimationFrameCallback {

    /**
     * 当前操作对象
     */
    private WeakReference<Object> target;

    /**
     * 是否重复
     */
    private boolean repeat = false;

    /**
     * 运行时长
     */
    private long mDuration = 300;

    /**
     * 差值器
     */
    private TimeInterpolator timeInterpolator;

    /**
     * 动画助理，用于设置属性值
     */
    private MyFloatPropertyValuesHolder myFloatPropertyValuesHolder;

    /**
     * 当前执行的次数
     */
    private float index = 0;

    /**
     * 构造方法
     * <p>
     * 初始化动画助理，用于设置参数，并将动画进行分解成N个关键帧，
     *
     * @param target       动画对象
     * @param propertyName 需要修改的属性名, View 中的属性名且必须有对应 setter 和 getter
     * @param values       关键帧的参数
     */
    public MyObjectAnimator(Object target, String propertyName, float... values) {
        this.target = new WeakReference<>(target);
        myFloatPropertyValuesHolder = new MyFloatPropertyValuesHolder(propertyName, values);
    }

    /**
     * 初始化动画任务
     *
     * @param target
     * @param propertyName
     * @param values
     * @return
     */
    public static MyObjectAnimator ofFloat(Object target, String propertyName, float... values) {
        MyObjectAnimator anim = new MyObjectAnimator(target, propertyName, values);
        return anim;
    }


    /**
     * 设置时间插值器
     *
     * @param timeInterpolator
     */
    public void setTimeInterpolator(TimeInterpolator timeInterpolator) {
        this.timeInterpolator = timeInterpolator;
    }

    /**
     * 设置是否重复
     * @param repeat
     */
    public void setRepeat(boolean repeat) {
        this.repeat = repeat;
    }

    /**
     * 设置时长
     * @param duration
     */
    public void setDuration(long duration) {
        this.mDuration = duration;
    }

    /**
     * 每隔16ms 会收到信号回调，并进行对应的属性设值
     *
     * @param currentTime
     * @return
     */
    @Override
    public boolean doAnimationFrame(long currentTime) {
        // 获得到 VSync 信号, 开始属性动画

        // 获得应该被执行的总次数
        float total = mDuration / 16;

        // 计算当前执行百分比（index++）/total
        float fraction = (index++) / total;
        if (timeInterpolator != null) {
            fraction = timeInterpolator.getInterpolation(fraction);
        }

        // 是否重复播放
        if (index >= total) {
            index = 0;

            if (!repeat) {
                // 不重复，移除监听
                VSYNCManager.getInstance().removeCallbacks(this);
                return true;
            }
        }

        // 通过 动画属性助理 设置 对应属性值，以完成动画操作
        myFloatPropertyValuesHolder.setAnimatedValue(target.get(), fraction);
        return false;
    }

    /**
     * 启动动画
     */
    public void start() {
        // 注册监听
        VSYNCManager.getInstance().addCallbacks(this);
    }
}
