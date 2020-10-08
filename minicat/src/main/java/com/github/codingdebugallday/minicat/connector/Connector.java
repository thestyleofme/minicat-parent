package com.github.codingdebugallday.minicat.connector;

import java.io.IOException;

import com.github.codingdebugallday.minicat.container.Server;
import com.github.codingdebugallday.minicat.container.Service;
import com.github.codingdebugallday.minicat.life.BaseLifecycle;

/**
 * <p>
 * description
 * </p>
 *
 * @author isaac 2020/10/08 19:48
 * @since 1.0.0
 */
public class Connector extends BaseLifecycle {

    private Integer port;
    private Service service;
    private Server server;
    private final ProtocolHandler protocolHandler;

    public Connector(ProtocolHandler protocolHandler) {
        this.protocolHandler = protocolHandler;
    }

    @Override
    protected void initInternal() {
        try {
            protocolHandler.init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void startInternal() {
        try {
            protocolHandler.setExecutor(server.getExecutor());
            protocolHandler.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }
}
