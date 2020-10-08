package com.github.codingdebugallday.demo1.servlet;

import java.io.IOException;

import com.github.codingdebugallday.minicat.server.Request;
import com.github.codingdebugallday.minicat.server.Response;
import com.github.codingdebugallday.minicat.servlet.HttpServlet;
import com.github.codingdebugallday.minicat.utils.HttpProtocolUtil;

/**
 * <p>
 * description
 * </p>
 *
 * @author isaac 2020/10/08 21:17
 * @since 1.0.0
 */
public class Demo1Servlet extends HttpServlet {

    @Override
    public void doGet(Request request, Response response) {
        String content = "<h1>Demo1Servlet get " + request.getContext().getContextName() + "</h1>";
        try {
            response.output(HttpProtocolUtil.getHttpHeader200(content.getBytes().length) + content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doPost(Request request, Response response) {
        String content = "<h1>Demo1Servlet post " + request.getContext().getContextName() + "</h1>";
        try {
            response.output(HttpProtocolUtil.getHttpHeader200(content.getBytes().length) + content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init() {

    }

    @Override
    public void destroy() {

    }
}
