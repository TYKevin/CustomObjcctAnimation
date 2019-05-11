package com.kevin.animator;

/**
 * 线性插值器
 *
 * 因为是线性运动，所以则传入百分比及输出百分比
 */
public class LineInterpolator implements TimeInterpolator {
    @Override
    public float getInterpolation(float input) {
        return input;
    }
}
