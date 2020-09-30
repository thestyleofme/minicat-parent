package com.github.codingdebugallday.minicat.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * <p>
 * Minicat的程序启动入口
 * </p>
 *
 * @author isaac 2020/09/30 2:13
 * @since 1.0.0
 */
public class Bootstrap {

    /**
     * 定义socket监听的端口
     */
    private int port = 8080;

    public static void main(String[] args) {
        Bootstrap bootstrap = new Bootstrap();
        try {
            // 启动
            bootstrap.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * minicat启动初始化操作
     */
    public void start() throws IOException {
        //===============================================================================
        // 完成minicat 2.0版本
        // 需求：封装Request和Response对象，返回html静态资源文件
        //===============================================================================
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("============>>> Minicat start on port: " + port);
        while (true) {
            Socket socket = serverSocket.accept();
            InputStream inputStream = socket.getInputStream();
            Request request = new Request(inputStream);
            OutputStream outputStream = socket.getOutputStream();
            Response response = new Response(outputStream);
            response.outputHtml(request.getUrl());
            socket.close();
        }
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
