package com.kevin.animator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 动画 "助理"
 * 【对应源码里 PropertyHolder】
 * 用于对当前控件进行设值，反射设置
 */
public class MyFloatPropertyValuesHolder {

    /**
     * 属性名
     */
    String mPropertyName;

    /**
     * float 类型 Class
     */
    Class mValueType;

    /**
     * 关键帧管理类
     */
    MyKeyframeSet myKeyframeSet;

    Method mSetter = null;

    /**
     * 构造方法
     * 初始化关键帧管理类
     *
     * @param propertyName 属性名
     * @param values 关键帧节点属性参数
     */
    public MyFloatPropertyValuesHolder(String propertyName, float... values) {
        this.mPropertyName = propertyName;
        mValueType = float.class;
        myKeyframeSet = MyKeyframeSet.ofFloat(values);

        setupSetter();
    }

    /**
     * 通过反射方法 Object.class.getMethod()方法生成对应的Setter方法
     */
    public void setupSetter() {
        // 获取对应属性的 Setter
        char firstLetter = Character.toUpperCase(mPropertyName.charAt(0));
        String theRest = mPropertyName.substring(1);
        String methodName = "set" + firstLetter + theRest;

        try {
            mSetter = Object.class.getMethod(methodName, float.class);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public void setAnimatedValue(Object target, float fraction) {
        // 通过当前的值 以及 执行百分比 计算出 需要修改的值
        Object value = myKeyframeSet.getValue(fraction);

        try {
            mSetter.invoke(target, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
