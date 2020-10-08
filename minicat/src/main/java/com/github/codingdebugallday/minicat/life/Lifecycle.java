package com.github.codingdebugallday.minicat.life;

/**
 * <p>
 * 定义生命周期：
 * 抽象各个容器的生命周期
 * init()
 * start()
 * stop()
 * destroy()
 * </p>
 *
 * @author isaac 2020/10/08 19:40
 * @since 1.0.0
 */
public interface Lifecycle {

    /**
     * 容器初始化接口
     */
    void init();

    /**
     * 容器启动接口
     */
    void start();
}
