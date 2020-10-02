package com.github.codingdebugallday.minicat.server;

import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.codingdebugallday.minicat.server.servlet.HttpServlet;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * <p>
 * Minicat的程序启动入口
 * </p>
 *
 * @author isaac 2020/09/30 2:13
 * @since 1.0.0
 */
public class Bootstrap {

    /**
     * 定义socket监听的端口
     */
    private int port = 8080;

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
    public void start() throws Exception {
        //===============================================================================
        // 完成minicat 3.0版本
        // 需求：封装Request和Response对象，返回html静态资源文件，还可以请求动态资源（Servlet）
        //===============================================================================

        // 加载解析相关的配置，web.xml
        loadServlet();
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("============>>> Minicat start on port: " + port);
        while (true) {
            Socket socket = serverSocket.accept();
            InputStream inputStream = socket.getInputStream();
            Request request = new Request(inputStream);
            OutputStream outputStream = socket.getOutputStream();
            Response response = new Response(outputStream);
            String url = request.getUrl();
            if (!serverMap.containsKey(url)) {
                // 静态资源处理
                response.outputHtml(url);
            } else {
                // 动态资源（servlet）处理
                HttpServlet httpServlet = serverMap.get(url);
                httpServlet.service(request, response);
            }
            socket.close();
        }
    }

    private final Map<String, HttpServlet> serverMap = new HashMap<>();

    /**
     * 加载解析web.xml，初始化Servlet
     */
    private void loadServlet() {
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("web.xml");
        SAXReader saxReader = new SAXReader();
        try {
            Document document = saxReader.read(resourceAsStream);
            Element rootElement = document.getRootElement();
            List<Element> servletElementList = rootElement.elements("servlet");
            for (Element servletElement : servletElementList) {
                Element servletNameElement = servletElement.element("servlet-name");
                String servletName = servletNameElement.getStringValue();
                Element servletClassElement = servletElement.element("servlet-class");
                String servletClass = servletClassElement.getStringValue();
                // 根据servlet-name的值找到url-pattern
                Element servletMappingElement = (Element) rootElement.selectSingleNode(
                        String.format("/web-app/servlet-mapping[servlet-name='%s']", servletName));
                String urlPattern = servletMappingElement.element("url-pattern").getStringValue();
                serverMap.put(urlPattern, (HttpServlet) Class.forName(servletClass)
                        .getClassLoader()
                        .loadClass(servletClass)
                        .getDeclaredConstructor()
                        .newInstance());
            }
        } catch (DocumentException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
