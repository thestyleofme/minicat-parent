package com.github.codingdebugallday.minicat.connector.impl;

import java.io.IOException;
import java.util.concurrent.Executor;

import com.github.codingdebugallday.minicat.connector.Connector;
import com.github.codingdebugallday.minicat.connector.Endpoint;
import com.github.codingdebugallday.minicat.connector.ProtocolHandler;

/**
 * <p>
 * http1.1
 * </p>
 *
 * @author isaac 2020/10/08 21:49
 * @since 1.0.0
 */
public class Http11ProtocolHandler implements ProtocolHandler {

    private Executor executor;
    private Connector connector;
    private Endpoint endpoint;

    public Http11ProtocolHandler(Endpoint endpoint) {
        this.endpoint = endpoint;
    }

    @Override
    public void setExecutor(Executor executor) {
        this.executor = executor;
    }

    @Override
    public Connector getConnector() {
        return this.connector;
    }

    @Override
    public void setConnector(Connector connector) {
        this.connector = connector;
    }

    @Override
    public Integer getPort() {
        return this.connector.getPort();
    }

    @Override
    public void init() throws IOException {
        endpoint.init();
        System.out.println(this.getClass().getName() + " init.");
    }

    @Override
    public void start() throws IOException {
        endpoint.start();
        System.out.println(this.getClass().getName() + " started.");
    }

    @Override
    public Executor getExecutor() {
        return this.executor;
    }
}
