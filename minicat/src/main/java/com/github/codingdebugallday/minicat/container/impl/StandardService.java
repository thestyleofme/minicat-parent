package com.github.codingdebugallday.minicat.container.impl;

import com.github.codingdebugallday.minicat.container.Engine;
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
public class StandardService extends BaseLifecycle implements Service {

    private Server server;

    private Engine[] engines = new Engine[0];

    @Override
    public void setServer(Server server) {
        this.server = server;
    }

    @Override
    public void addEngine(Engine engine) {
        Engine[] result = new Engine[engines.length + 1];
        System.arraycopy(engines, 0, result, 0, engines.length);
        result[engines.length] = engine;
        engines = result;
    }

    @Override
    public Engine[] getEngines() {
        return engines;
    }

    @Override
    protected void initInternal() {
        for (Engine engine : engines) {
            engine.init();
        }
    }

    @Override
    protected void startInternal() {
        for (Engine engine : engines) {
            engine.start();
        }
    }
}
