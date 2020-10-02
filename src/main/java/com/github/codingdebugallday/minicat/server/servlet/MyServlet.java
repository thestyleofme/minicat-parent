package com.github.codingdebugallday.minicat.server.servlet;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import com.github.codingdebugallday.minicat.server.Request;
import com.github.codingdebugallday.minicat.server.Response;
import com.github.codingdebugallday.minicat.server.utils.HttpProtocolUtil;

/**
 * <p>
 * description
 * </p>
 *
 * @author isaac 2020/10/03 0:04
 * @since 1.0.0
 */
public class MyServlet extends HttpServlet {

    @Override
    public void init() throws Exception {

    }

    @Override
    public void doGet(Request request, Response response) {
        String context = "<h1>my servlet get</h1>";
        try {
            response.output(HttpProtocolUtil.getHttpHeader200(context.getBytes(StandardCharsets.UTF_8).length) +
                    context);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doPost(Request request, Response response) {
        String context = "<h1>my servlet post</h1>";
        try {
            response.output(HttpProtocolUtil.getHttpHeader200(context.getBytes(StandardCharsets.UTF_8).length) +
                    context);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() throws Exception {

    }
}
