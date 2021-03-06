package com.github.codingdebugallday.minicat.container;

import com.github.codingdebugallday.minicat.servlet.Servlet;

/**
 * <p>
 * servlet容器
 * </p>
 *
 * @author isaac 2020/10/08 19:57
 * @since 1.0.0
 */
public interface Wrapper {

    /**
     * 获取servlet
     *
     * @return Servlet
     */
    Servlet getServlet();

    /**
     * 设置servlet
     *
     * @param servlet Servlet
     */
    void setServlet(Servlet servlet);

    /**
     * 设置URLPattern
     *
     * @param urlPattern urlPattern
     */
    void setUrlPattern(String urlPattern);

    /**
     * 获取urlPattern
     *
     * @return urlPattern
     */
    String getUrlPattern();

    /**
     * 设置ClassName
     *
     * @param className urlPattern
     */
    void setClassName(String className);

    /**
     * 获取className
     *
     * @return className
     */
    String getClassName();

    /**
     * 设置servletName
     *
     * @param servletName servletName
     */
    void setServletName(String servletName);

    /**
     * 获取servletName
     *
     * @return servletName
     */
    String getServletName();

    /**
     * 设置Context
     *
     * @param context Context
     */
    void setContext(Context context);
}
