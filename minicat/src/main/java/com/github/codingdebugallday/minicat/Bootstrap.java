package com.github.codingdebugallday.minicat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import com.github.codingdebugallday.minicat.adapter.Adapter;
import com.github.codingdebugallday.minicat.adapter.CoyoteAdapter;
import com.github.codingdebugallday.minicat.classloader.WebAppClassloader;
import com.github.codingdebugallday.minicat.connector.Connector;
import com.github.codingdebugallday.minicat.connector.Endpoint;
import com.github.codingdebugallday.minicat.connector.ProtocolHandler;
import com.github.codingdebugallday.minicat.connector.impl.BioEndpoint;
import com.github.codingdebugallday.minicat.connector.impl.Http11ProtocolHandler;
import com.github.codingdebugallday.minicat.container.*;
import com.github.codingdebugallday.minicat.container.impl.*;
import com.github.codingdebugallday.minicat.utils.ServerXmlResource;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * <p>
 * Minicat的程序启动入口
 * 开发流程：
 * 1、实现浏览器与minicat连接，给浏览器返回“hello, minicat”
 * 2、实现Request，Response的封装,实现静态资源的响应
 * 3、以及动态资源的访问
 * 4、使用线程池处理请求
 * 5、实现webapps部署多个项目
 * </p>
 *
 * @author isaac 2020/09/30 2:13
 * @since 1.0.0
 */
public class Bootstrap {

    private Server server;

    public static void main(String[] args) {
        Bootstrap bootstrap = new Bootstrap();
        try {
            // 启动
            bootstrap.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * minicat启动初始化操作
     */
    public void start() {
        // 加载配置文件server.xml以及初始化Context容器和Wrapper容器
        init();
        // 启动容器
        this.server.start();
    }

    private void init() {
        try {
            load();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        server.init();
    }

    private void load() throws FileNotFoundException {
        this.server = createServer();
        Document serverDocument = ServerXmlResource.getServerDocument();
        Element rootElement = serverDocument.getRootElement();
        // 初始化service
        List<Element> servicesList = rootElement.elements("Service");
        Service[] services = new Service[servicesList.size()];
        for (int i = 0; i < servicesList.size(); i++) {
            services[i] = createService(this.server, servicesList.get(i));
        }
        this.server.setServices(services);
    }

    private Service createService(Server server, Element element) throws FileNotFoundException {
        List<Element> connectorList = element.elements("Connector");
        List<Element> engineList = element.elements("Engine");
        Service service = new StandardService();
        for (Element engineElement : engineList) {
            Engine engine = createEngine(engineElement);
            service.addEngine(engine);
        }
        service.setServer(server);
        for (Element elementConnector : connectorList) {
            String connectorPort = elementConnector.attributeValue("port");
            Adapter adapter = new CoyoteAdapter();
            Endpoint endpoint = new BioEndpoint(adapter);
            ProtocolHandler protocolHandler = new Http11ProtocolHandler(endpoint);
            Connector connector = new Connector(protocolHandler);
            adapter.setConnector(connector);
            protocolHandler.setConnector(connector);
            endpoint.setProtocolHandler(protocolHandler);
            connector.setPort(Integer.valueOf(connectorPort));
            connector.setServer(server);
            connector.setService(service);
            server.addConnector(connector);
        }
        return service;
    }

    private Engine createEngine(Element element) throws FileNotFoundException {
        List<Element> hostList = element.elements("Host");
        Engine engine = new StandardEngine();
        Host[] hosts = new Host[hostList.size()];
        for (int i = 0; i < hostList.size(); i++) {
            hosts[i] = createHost(engine, hostList.get(i));
        }
        engine.setHosts(hosts);
        return engine;
    }

    private Host createHost(Engine engine, Element element) throws FileNotFoundException {
        Host host = new StandardHost();
        String name = element.attributeValue("name");
        String appBase = element.attributeValue("appBase");
        host.setName(name);
        host.setAppBase(appBase);
        host.setEngine(engine);

        File baseFile = new File(appBase);
        File[] files = baseFile.listFiles();
        if (files != null) {
            for (File web : files) {
                if (web.isDirectory()) {
                    // 初始化Context
                    Context context = createContext(web);
                    host.addContext(context);
                }
            }
        }
        return host;
    }

    private Context createContext(File web) throws FileNotFoundException {
        Context context = new StandardContext();
        String name = web.getName();
        context.setContextName(name);
        File classPath = new File(web, "WEB-INF/classes");
        URL[] urls = new URL[1];
        try {
            urls[0] = classPath.toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        context.setClassloader(new WebAppClassloader(urls));
        // 加载web.xml,并解析出Wrapper
        File webXml = new File(web, "WEB-INF/web.xml");
        if (webXml.exists()) {
            createWrapper(context, webXml);
        }
        return context;
    }

    private void createWrapper(Context context, File webXml) throws FileNotFoundException {
        InputStream inputStream = new FileInputStream(webXml);
        SAXReader saxReader = new SAXReader();
        try {
            Document document = saxReader.read(inputStream);
            Element rootElement = document.getRootElement();
            List<Element> servletElementList = rootElement.elements("servlet");
            for (Element element : servletElementList) {
                Element servletNameEle = (Element) element.selectSingleNode("//servlet-name");
                String servletName = servletNameEle.getStringValue();
                Element servletClsEle = (Element) element.selectSingleNode("//servlet-class");
                String servletClass = servletClsEle.getStringValue();

                // 根据servlet-name的值找到url-pattern
                Element servletMapping = (Element) rootElement.selectSingleNode("/web-app/servlet-mapping[servlet-name='" + servletName + "']");
                String urlPattern = servletMapping.selectSingleNode("url-pattern").getStringValue();

                Wrapper wrapper = new StandardWrapper();
                wrapper.setServletName(servletName);
                wrapper.setClassName(servletClass);
                wrapper.setUrlPattern(urlPattern);
                wrapper.setContext(context);

                context.addWrapper(wrapper);
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    private Server createServer() {
        return new StandardServer();
    }

}
