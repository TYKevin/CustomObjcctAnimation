package com.kevin.animator;

import java.util.ArrayList;
import java.util.List;

/**
 * 模拟 VSync 信号
 *
 * 代码中使用线程模拟VSync信号，因为在第三方中无法监听到 VSync 信号。
 */
public class VSYNCManager {
    private static final VSYNCManager mInstance = new VSYNCManager();

    private List<AnimationFrameCallback> callbacks = new ArrayList<>();

    public static VSYNCManager getInstance() {
        return mInstance;
    }

    /**
     * 构造方法，启动模拟VSYNC 信号的线程
     */
    private VSYNCManager() {
        new Thread(runnable).start();
    }

    /**
     * 添加监听
     * @param callback
     */
    public void addCallbacks(AnimationFrameCallback callback) {
        if (callback == null) {
            return;
        }

        callbacks.add(callback);
    }


    /**
     * 移除监听
     * @param callback
     */
    public void removeCallbacks(AnimationFrameCallback callback) {
        if (callback == null) {
            return;
        }

        callbacks.remove(callback);
    }

    /**
     * 模拟VSYNC信号，信号每16ms 发出一次
     */
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            while (true) {
                try {
                    // 60Hz（16ms）绘制一次
                    Thread.sleep(16);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // 模拟 VSync 信号
                for(AnimationFrameCallback callback:callbacks) {
                    callback.doAnimationFrame(System.currentTimeMillis());
                }
            }
        }
    };


    /**
     * 信号监听的方法，当有信号传递的时候，进行回调
     */
    public interface AnimationFrameCallback {
        boolean doAnimationFrame(long currentTime);
    }
}
