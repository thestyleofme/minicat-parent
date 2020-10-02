package com.github.codingdebugallday.minicat.utils;

import java.nio.charset.StandardCharsets;

/**
 * <p>
 * description
 * </p>
 *
 * @author isaac 2020/09/30 2:37
 * @since 1.0.0
 */
public class HttpProtocolUtil {

    private HttpProtocolUtil() {
    }

    public static String getHttpHeader200(long contentLength) {
        return "HTTP/1.1 200 OK \n" +
                "Content-Type: text/html;charset=utf-8 \n" +
                "Content-Length: " + contentLength + " \n" +
                "\r\n";
    }

    public static String getHttpHeader404() {
        String str404 = "<h1>404 not found</h1>";
        return "HTTP/1.1 404 NOT Found \n" +
                "Content-Type: text/html;charset=utf-8 \n" +
                "Content-Length: " + str404.getBytes(StandardCharsets.UTF_8).length + " \n" +
                "\r\n" +
                str404;
    }
}
