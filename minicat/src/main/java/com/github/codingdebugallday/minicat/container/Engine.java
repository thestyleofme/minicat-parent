package com.github.codingdebugallday.minicat.container;

import com.github.codingdebugallday.minicat.life.Lifecycle;

/**
 * <p>
 * description
 * </p>
 *
 * @author isaac 2020/10/08 19:56
 * @since 1.0.0
 */
public interface Engine extends Lifecycle {
    /**
     * 获取服务容器
     *
     * @return Service
     */
    Service getService();

    /**
     * 设置服务容器
     *
     * @param service Service
     */
    void setService(Service service);

    /**
     * 设置Host
     *
     * @param hosts Host
     */
    void setHosts(Host[] hosts);

    /**
     * 获取host列表
     *
     * @return Host[]
     */
    Host[] getHosts();
}

