package com.kevin.animator;

import android.animation.FloatEvaluator;
import android.animation.TypeEvaluator;

import java.util.Arrays;
import java.util.List;

/**
 * 关键帧管理类
 */
public class MyKeyframeSet {

    /**
     * 第一个关键帧
     */
    MyFloatKeyframe mFirstKeyframe;

    /**
     * 帧队列，mFirstKeyframe 其实就是第0个元素
     */
    List<MyFloatKeyframe> myFloatKeyframes;

    /**
     * 类型估值器
     *
     * 相当于差速器，用在关键帧中间穿插，进行不同速度的处理。
     */
    TypeEvaluator mTypeEvaluator;

    /**
     * 构造函数
     * 初始化传入的关键帧数组，以及估值器
     * @param keyframes
     */
    public MyKeyframeSet(MyFloatKeyframe... keyframes) {
        myFloatKeyframes = Arrays.asList(keyframes);
        mFirstKeyframe = keyframes[0];
        // 这里的类型估值器，我们直接使用Android原生的 Float类型估值器，android.animation.FloatEvaluator
        mTypeEvaluator = new FloatEvaluator();
    }

    /**
     * 根据传入的节点参数，生成关键帧
     *
     * @param values
     * @return
     */
    public static MyKeyframeSet ofFloat(float... values) {
        // 总共有多少关键帧节点
        int frameCount = values.length;

        // 遍历关键帧参数，并生成关键帧
        MyFloatKeyframe[] myFloatKeyframes = new MyFloatKeyframe[frameCount];
        // 初始化第一帧 关键帧，默认百分比为 0
        myFloatKeyframes[0] = new MyFloatKeyframe(0, values[0]);

        // 遍历关键帧节点数量，并计算关键帧处于的百分比，初始化对应的关键帧
        for (int i = 1; i < frameCount; ++i) {
            myFloatKeyframes[i] = new MyFloatKeyframe((float) i / (frameCount - 1), values[i]);
        }
        return new MyKeyframeSet(myFloatKeyframes);
    }

    /**
     * 通过传入的百分比，计算最终需要设置的具体属性值
     * @param fraction
     * @return
     */
    public Object getValue(float fraction) {
        // 关键帧之间 的位置 根据执行时间，进行计算状态

        // 先拿到第一帧
        MyFloatKeyframe prevKeyframe = mFirstKeyframe;

        // 遍历所有关键帧
        for (int i = 1; i < myFloatKeyframes.size(); ++i) {
            // 下一帧
            MyFloatKeyframe nextKeyframe = myFloatKeyframes.get(i);

            // 【关键】每个关键帧与关键帧之间动画状态的计算（公式见 方法中）
            // 使用估值器计算，如果当前运行百分比在这两帧中间，则将当前百分比的上下关键帧的对应参数和当前运行百分比传入估值器，得到当前百分比所对应的数值
            if (fraction < nextKeyframe.getFraction()) {
                return mTypeEvaluator.evaluate(fraction, prevKeyframe.getValue(), nextKeyframe.getValue());
            }

            prevKeyframe = nextKeyframe;
        }
        return null;
    }
}
