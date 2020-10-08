package com.github.codingdebugallday.minicat.container;

import java.util.concurrent.Executor;

import com.github.codingdebugallday.minicat.connector.Connector;
import com.github.codingdebugallday.minicat.life.Lifecycle;

/**
 * <p>
 * description
 * </p>
 *
 * @author isaac 2020/10/08 19:45
 * @since 1.0.0
 */
public interface Server extends Lifecycle {

    /**
     * 设置Service集合
     *
     * @param services Service[]
     */
    void setServices(Service[] services);

    /**
     * 获取Service集合
     *
     * @return Service[]
     */
    Service[] findServices();

    /**
     * 新增Connector
     *
     * @param connector Connector
     */
    void addConnector(Connector connector);

    /**
     * 获取 Executor
     *
     * @return Executor
     */
    Executor getExecutor();
}
