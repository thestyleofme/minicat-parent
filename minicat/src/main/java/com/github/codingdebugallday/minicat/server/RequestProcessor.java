package com.github.codingdebugallday.minicat.server;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Map;

import com.github.codingdebugallday.minicat.servlet.HttpServlet;

/**
 * <p>
 * description
 * </p>
 *
 * @author isaac 2020/10/03 1:25
 * @since 1.0.0
 */
public class RequestProcessor implements Runnable {

    private final Socket socket;
    private final Map<String, HttpServlet> serverMap;

    public RequestProcessor(Socket socket, Map<String, HttpServlet> serverMap) {
        this.socket = socket;
        this.serverMap = serverMap;
    }

    @Override
    public void run() {
        try {
            InputStream inputStream = socket.getInputStream();
            Request request = new Request(inputStream);
            OutputStream outputStream = socket.getOutputStream();
            Response response = new Response(outputStream);
            String url = request.getUrl();
            if (!serverMap.containsKey(url)) {
                // 静态资源处理
                response.outputHtml(url);
            } else {
                // 动态资源（servlet）处理
                HttpServlet httpServlet = serverMap.get(url);
                httpServlet.service(request, response);
            }
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
