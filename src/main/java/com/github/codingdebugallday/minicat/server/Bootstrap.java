package com.github.codingdebugallday.minicat.server;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import com.github.codingdebugallday.minicat.server.utils.HttpProtocolUtil;

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
        // 完成minicat 1.0版本
        // 需求：浏览器请求http://localhost:8080，返回一个固定的字符串到页面 "hello Minicat!"
        //===============================================================================
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("============>>> Minicat start on port: " + port);
        while (true) {
            Socket socket = serverSocket.accept();
            OutputStream outputStream = socket.getOutputStream();
            String data = "hello Minicat!";
            String httpHeader200 = HttpProtocolUtil.getHttpHeader200(data.getBytes(StandardCharsets.UTF_8).length);
            // String httpHeader200 = HttpProtocolUtil.getHttpHeader404();
            String responseText = httpHeader200 + data;
            outputStream.write(responseText.getBytes(StandardCharsets.UTF_8));
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
