package com.github.codingdebugallday.minicat.servlet;

import com.github.codingdebugallday.minicat.server.Request;
import com.github.codingdebugallday.minicat.server.Response;

/**
 * <p>
 * description
 * </p>
 *
 * @author isaac 2020/10/02 23:55
 * @since 1.0.0
 */
public interface Servlet {

    /**
     * init
     */
    void init();

    /**
     * destroy
     */
    void destroy();

    /**
     * service
     *
     * @param request  Request
     * @param response Response
     */
    void service(Request request, Response response);

}
