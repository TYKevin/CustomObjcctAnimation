package com.kevin.animator;

/**
 * 时间差值器
 *
 * 就是一个接口，用于修改执行百分比，以修改运行时的状态
 */
public interface TimeInterpolator {
    float getInterpolation(float input);
}
