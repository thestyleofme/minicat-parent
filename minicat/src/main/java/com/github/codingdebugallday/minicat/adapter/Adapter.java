package com.github.codingdebugallday.minicat.adapter;

import com.github.codingdebugallday.minicat.connector.Connector;
import com.github.codingdebugallday.minicat.server.Request;
import com.github.codingdebugallday.minicat.server.Response;

/**
 * <p>
 * description
 * </p>
 *
 * @author isaac 2020/10/08 19:54
 * @since 1.0.0
 */
public interface Adapter {

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
     * Connector
     *
     * @param request  Request
     * @param response Response
     */
    void service(Request request, Response response);
}
