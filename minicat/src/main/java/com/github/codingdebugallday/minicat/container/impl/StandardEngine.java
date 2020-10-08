package com.github.codingdebugallday.minicat.container.impl;


import com.github.codingdebugallday.minicat.container.Engine;
import com.github.codingdebugallday.minicat.container.Host;
import com.github.codingdebugallday.minicat.container.Service;
import com.github.codingdebugallday.minicat.life.BaseLifecycle;

/**
 * <p>
 * description
 * </p>
 *
 * @author isaac 2020/10/08 20:02
 * @since 1.0.0
 */
public class StandardEngine extends BaseLifecycle implements Engine {

    private Service service;
    private Host[] hosts = new Host[0];

    @Override
    public Service getService() {
        return this.service;
    }

    @Override
    public void setService(Service service) {
        this.service = service;
    }

    @Override
    public void setHosts(Host[] hosts) {
        this.hosts = hosts;
    }

    @Override
    public Host[] getHosts() {
        return this.hosts;
    }

    @Override
    protected void initInternal() {
        for (Host host : hosts) {
            host.init();
        }
    }

    @Override
    protected void startInternal() {
        for (Host host : hosts) {
            host.start();
        }
    }
}
