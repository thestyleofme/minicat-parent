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
     *
     * @throws Exception e
     */
    void init() throws Exception;

    /**
     * destroy
     *
     * @throws Exception e
     */
    void destroy() throws Exception;

    /**
     * service
     *
     * @param request  Request
     * @param response Response
     * @throws Exception e
     */
    void service(Request request, Response response) throws Exception;

}
