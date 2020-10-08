package com.github.codingdebugallday.minicat.server;

import java.io.IOException;
import java.io.InputStream;

import com.github.codingdebugallday.minicat.container.Context;

/**
 * <p>
 * 把请求信息封装为Request对象（根据socket.getInputStream()进行封装）
 * </p>
 *
 * @author isaac 2020/10/01 0:56
 * @since 1.0.0
 */
public class Request {

    private InputStream inputStream;

    private String method;
    private String url;
    /**
     * 虚拟主机
     */
    private String host;
    /**
     * 请求上下文
     */
    private Context context;

    public Request() {
    }

    //===============================================================================
    // GET / HTTP/1.1
    // Host: localhost:8080
    // Connection: keep-alive
    // Cache-Control: max-age=0
    // Upgrade-Insecure-Requests: 1
    // User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.105 Safari/537.36
    // Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9
    // Sec-Fetch-Site: cross-site
    // Sec-Fetch-Mode: navigate
    // Sec-Fetch-Dest: document
    // Accept-Encoding: gzip, deflate, br
    // Accept-Language: zh-CN,zh;q=0.9
    //===============================================================================

    public Request(InputStream inputStream) throws IOException {
        this.inputStream = inputStream;
        // 从输入流中获取请求信息
        int count = 0;
        while (count == 0) {
            count = inputStream.available();
        }
        byte[] bytes = new byte[count];
        inputStream.read(bytes);
        String inputStr = new String(bytes);
        // 获取第一行请求头信息 GET / HTTP/1.1
        String firstLineStr = inputStr.split("\\n")[0];
        String[] split = firstLineStr.split(" ");
        this.method = split[0];
        this.url = split[1];
        System.out.println("============>>> method: " + method);
        System.out.println("============>>> url: " + url);
        // 获取第二行
        String secondLineStr = inputStr.split("\\n")[1].replace(" ", "");
        String[] split1 = secondLineStr.split(":");
        this.host = split1[1];
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
