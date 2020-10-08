package com.github.codingdebugallday.minicat.container;

import com.github.codingdebugallday.minicat.life.Lifecycle;

/**
 * <p>
 * 应用上下文
 * </p>
 *
 * @author isaac 2020/10/08 19:56
 * @since 1.0.0
 */
public interface Context extends Lifecycle {

    /**
     * 设置应用上下文名称
     *
     * @param contextName contextName
     */
    void setContextName(String contextName);

    /**
     * 获取应用上下文名称
     *
     * @return contextName
     */
    String getContextName();

    /**
     * 添加servlet容器
     *
     * @param wrapper Wrapper
     */
    void addWrapper(Wrapper wrapper);

    /**
     * 查找Servlet
     *
     * @param servletName servletName
     * @return Wrapper
     */
    Wrapper findWrapper(String servletName);

    /**
     * 设置web的ClassLoader
     *
     * @param classloader ClassLoader
     */
    void setClassloader(ClassLoader classloader);

}
