package com.github.codingdebugallday.minicat.connector;

import java.io.IOException;
import java.util.concurrent.Executor;

/**
 * <p>
 * description
 * </p>
 *
 * @author isaac 2020/10/08 19:50
 * @since 1.0.0
 */
public interface ProtocolHandler {

    /**
     * 设置Executor
     *
     * @param executor Executor
     */
    void setExecutor(Executor executor);

    /**
     * 获取Executor
     *
     * @return Executor
     */
    Executor getExecutor();

    /**
     * 获取Connector
     *
     * @return Connector
     */
    Connector getConnector();

    /**
     * 设置Connector
     *
     * @param connector Connector
     */
    void setConnector(Connector connector);

    /**
     * 获取端口
     *
     * @return Integer
     */
    Integer getPort();

    /**
     * init
     *
     * @throws IOException IOException
     */
    void init() throws IOException;

    /**
     * start
     *
     * @throws IOException IOException
     */
    void start() throws IOException;

}
