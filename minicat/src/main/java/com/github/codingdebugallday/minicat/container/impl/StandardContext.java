package com.github.codingdebugallday.minicat.container.impl;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.github.codingdebugallday.minicat.container.Context;
import com.github.codingdebugallday.minicat.container.Wrapper;
import com.github.codingdebugallday.minicat.life.BaseLifecycle;
import com.github.codingdebugallday.minicat.servlet.Servlet;

/**
 * <p>
 * description
 * </p>
 *
 * @author isaac 2020/10/08 19:59
 * @since 1.0.0
 */
public class StandardContext extends BaseLifecycle implements Context {

    private final Map<String, Wrapper> wrapperMap = new ConcurrentHashMap<>();
    private String contextName;
    private ClassLoader classLoader;

    @Override
    public void setContextName(String contextName) {
        this.contextName = contextName;
    }

    @Override
    public String getContextName() {
        return this.contextName;
    }

    @Override
    public void addWrapper(Wrapper wrapper) {
        wrapperMap.put(wrapper.getUrlPattern(), wrapper);
    }

    @Override
    public Wrapper findWrapper(String urlPattern) {
        return wrapperMap.get(urlPattern);
    }

    @Override
    public void setClassloader(ClassLoader classloader) {
        this.classLoader = classloader;
    }

    @Override
    protected void initInternal() {
        Set<Map.Entry<String, Wrapper>> entries = wrapperMap.entrySet();
        for (Map.Entry<String, Wrapper> entry : entries) {
            Wrapper value = entry.getValue();
            try {
                Class<?> aClass = this.classLoader.loadClass(value.getClassName());
                Servlet servlet = (Servlet) aClass.getDeclaredConstructor().newInstance();
                value.setServlet(servlet);
                servlet.init();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void startInternal() {
        System.out.println(this.getClass().getName() + " startContext");
    }
}
