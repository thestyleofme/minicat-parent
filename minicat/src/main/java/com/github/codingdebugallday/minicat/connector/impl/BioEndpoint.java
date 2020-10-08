package com.github.codingdebugallday.minicat.connector.impl;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.github.codingdebugallday.minicat.adapter.Adapter;
import com.github.codingdebugallday.minicat.connector.Endpoint;
import com.github.codingdebugallday.minicat.connector.ProtocolHandler;
import com.github.codingdebugallday.minicat.processor.Processor;

/**
 * <p>
 * description
 * </p>
 *
 * @author isaac 2020/10/08 19:53
 * @since 1.0.0
 */
public class BioEndpoint implements Endpoint {

    private ProtocolHandler protocolHandler;
    private final Adapter adapter;
    private ServerSocket serverSocket;

    public BioEndpoint(Adapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public void setProtocolHandler(ProtocolHandler protocolHandler) {
        this.protocolHandler = protocolHandler;
    }

    @Override
    public void init() throws IOException {
        bind();
    }

    @Override
    public void start() throws IOException {
        while (true) {
            // 开始接收请求
            Socket accept = this.serverSocket.accept();
            // 创建Processor处理请求
            Processor processor = createProcessor(accept, adapter);
            protocolHandler.getExecutor().execute(processor);
        }
    }

    private Processor createProcessor(Socket accept, Adapter adapter) {
        return new Processor(accept, adapter);
    }

    @Override
    public void bind() throws IOException {
        serverSocket = new ServerSocket(protocolHandler.getPort());
        System.out.println(this.getClass().getName() + " init port " + protocolHandler.getPort());
    }

}
