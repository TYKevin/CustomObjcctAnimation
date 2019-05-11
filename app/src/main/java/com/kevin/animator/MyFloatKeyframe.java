package com.kevin.animator;

/**
 * 关键帧 保存某一时刻控件的具体状态
 *
 * 初始化动画任务的时候已经完成
 */
public class MyFloatKeyframe {
    /**
     * 当前的帧所处的百分比， 范围 0 - 1
     */
    float mFraction;
    /**
     * 每一个关键帧，具体设置的参数
     */
    float mValue;

    Class mValueType;
    public MyFloatKeyframe(float mFraction, float mValue) {
        this.mFraction = mFraction;
        this.mValue = mValue;
        this.mValueType = float.class;
    }

    public float getFraction() {
        return mFraction;
    }

    public float getValue() {
        return mValue;
    }
}
