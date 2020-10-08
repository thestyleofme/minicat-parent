package com.github.codingdebugallday.minicat.life;

/**
 * <p>
 * description
 * </p>
 *
 * @author isaac 2020/10/08 19:42
 * @since 1.0.0
 */
public abstract class BaseLifecycle implements Lifecycle {

    /**
     * 初始化容器
     */
    protected abstract void initInternal();

    @Override
    public void init() {
        System.out.println(this.getClass().getName() + "  init begin");
        initInternal();
        System.out.println(this.getClass().getName() + "  init finished");
    }

    /**
     * 启动容器
     */
    protected abstract void startInternal();

    @Override
    public void start() {
        System.out.println(this.getClass().getName() + "  start begin");
        startInternal();
        System.out.println(this.getClass().getName() + "  start finished");
    }
}
