package com.github.codingdebugallday.minicat.container.impl;

import com.github.codingdebugallday.minicat.container.Context;
import com.github.codingdebugallday.minicat.container.Engine;
import com.github.codingdebugallday.minicat.container.Host;
import com.github.codingdebugallday.minicat.life.BaseLifecycle;

/**
 * <p>
 * description
 * </p>
 *
 * @author isaac 2020/10/08 20:02
 * @since 1.0.0
 */
public class StandardHost extends BaseLifecycle implements Host {

    private Engine engine;
    private String name;
    private String appBase;
    private Context[] contexts = new Context[0];

    @Override
    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    @Override
    public Engine getEngine() {
        return this.engine;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setAppBase(String appbase) {
        this.appBase = appbase;
    }

    @Override
    public String getAppBase() {
        return this.appBase;
    }

    @Override
    public void addContext(Context context) {
        Context[] result = new Context[contexts.length + 1];
        System.arraycopy(this.contexts, 0, result, 0, this.contexts.length);
        result[contexts.length] = context;
        this.contexts = result;
    }

    @Override
    public Context[] getContextList() {
        return this.contexts;
    }

    @Override
    protected void initInternal() {
        for (Context context : contexts) {
            context.init();
        }
    }

    @Override
    protected void startInternal() {
        for (Context context : contexts) {
            context.start();
        }
    }
}
