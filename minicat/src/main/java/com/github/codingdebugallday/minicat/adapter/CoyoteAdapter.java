package com.github.codingdebugallday.minicat.adapter;

import java.io.IOException;

import com.github.codingdebugallday.minicat.connector.Connector;
import com.github.codingdebugallday.minicat.container.Context;
import com.github.codingdebugallday.minicat.container.Engine;
import com.github.codingdebugallday.minicat.container.Host;
import com.github.codingdebugallday.minicat.container.Wrapper;
import com.github.codingdebugallday.minicat.server.Request;
import com.github.codingdebugallday.minicat.server.Response;

/**
 * <p>
 * description
 * </p>
 *
 * @author isaac 2020/10/08 19:55
 * @since 1.0.0
 */
public class CoyoteAdapter implements Adapter {

    private Connector connector;

    @Override
    public void setConnector(Connector connector) {
        this.connector = connector;
    }

    @Override
    public Connector getConnector() {
        return this.connector;
    }

    @Override
    public void service(Request request, Response response) {
        // 查找Context
        Engine[] engines = this.connector.getService().getEngines();
        boolean isInHost = false;
        for (Engine engine : engines) {
            Host[] hosts = engine.getHosts();
            for (Host host : hosts) {
                if (!host.getName().equalsIgnoreCase(request.getHost())) {
                    continue;
                }
                Context[] contextList = host.getContextList();
                for (Context context : contextList) {
                    String[] split1 = request.getUrl().split("/");
                    if (context.getContextName().equalsIgnoreCase(split1[1])) {
                        // 查找上下文
                        request.setContext(context);
                        response.setContext(context);
                        isInHost = true;
                        response.setHost(host);
                        break;
                    }
                }

                if (isInHost) {
                    break;
                }
            }
            if (isInHost) {
                break;
            }
        }

        // servlet或静态资源处理
        if (request.getContext() != null) {
            String contextName = request.getContext().getContextName();
            String urlPattern = request.getUrl().replaceFirst("/" + contextName, "");
            Wrapper wrapper = request.getContext().findWrapper(urlPattern);
            if (wrapper == null) {
                try {
                    response.outputHtml(request.getUrl());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                wrapper.getServlet().service(request, response);
            }
        }
    }
}
