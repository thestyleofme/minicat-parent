package com.github.codingdebugallday.minicat.servlet;

import com.github.codingdebugallday.minicat.server.Request;
import com.github.codingdebugallday.minicat.server.Response;

/**
 * <p>
 * description
 * </p>
 *
 * @author isaac 2020/10/02 23:57
 * @since 1.0.0
 */
public abstract class HttpServlet implements Servlet {

    /**
     * doGet
     *
     * @param request  Request
     * @param response Response
     */
    public abstract void doGet(Request request, Response response);

    /**
     * doPost
     *
     * @param request  Request
     * @param response Response
     */
    public abstract void doPost(Request request, Response response);

    @Override
    public void service(Request request, Response response) throws Exception {
        if ("GET".equalsIgnoreCase(request.getMethod())) {
            doGet(request, response);
        } else if ("POST".equalsIgnoreCase(request.getMethod())) {
            doPost(request, response);
        }
    }
}
