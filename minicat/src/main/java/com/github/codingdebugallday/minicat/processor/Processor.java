package com.github.codingdebugallday.minicat.processor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.github.codingdebugallday.minicat.adapter.Adapter;
import com.github.codingdebugallday.minicat.server.Request;
import com.github.codingdebugallday.minicat.server.Response;

/**
 * <p>
 * description
 * </p>
 *
 * @author isaac 2020/10/08 20:11
 * @since 1.0.0
 */
public class Processor implements Runnable {

    private final Socket socket;
    private final Adapter adapter;

    public Processor(Socket socket, Adapter adapter) {
        this.socket = socket;
        this.adapter = adapter;
    }

    @Override
    public void run() {
        try {
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();
            Request request = new Request(inputStream);
            Response response = new Response(outputStream);
            adapter.service(request, response);
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
