package com.github.codingdebugallday.minicat.container.impl;


import java.util.concurrent.*;

import com.github.codingdebugallday.minicat.connector.Connector;
import com.github.codingdebugallday.minicat.container.Server;
import com.github.codingdebugallday.minicat.container.Service;
import com.github.codingdebugallday.minicat.life.BaseLifecycle;

/**
 * <p>
 * description
 * </p>
 *
 * @author isaac 2020/10/08 21:43
 * @since 1.0.0
 */
public final class StandardServer extends BaseLifecycle implements Server {

    private Service[] services = new Service[0];
    private Connector[] connectors = new Connector[0];

    private Executor executor;

    @Override
    protected void initInternal() {
        for (Service service : services) {
            service.init();
        }
        for (Connector connector : connectors) {
            connector.init();
        }
        // 定义线程池
        this.executor = new ThreadPoolExecutor(10,
                50,
                100L,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(50),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
    }

    @Override
    protected void startInternal() {
        for (Service service : services) {
            service.start();
        }
        for (Connector connector : connectors) {
            connector.start();
        }
    }

    @Override
    public void setServices(Service[] services) {
        this.services = services;
    }

    @Override
    public Service[] findServices() {
        return this.services;
    }

    @Override
    public void addConnector(Connector connector) {
        Connector[] result = new Connector[connectors.length + 1];
        System.arraycopy(connectors, 0, result, 0, connectors.length);
        result[connectors.length] = connector;
        connectors = result;
    }

    @Override
    public Executor getExecutor() {
        return this.executor;
    }

}
