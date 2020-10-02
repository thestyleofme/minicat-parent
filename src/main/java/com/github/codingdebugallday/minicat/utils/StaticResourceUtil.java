package com.github.codingdebugallday.minicat.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * <p>
 * description
 * </p>
 *
 * @author isaac 2020/10/01 1:28
 * @since 1.0.0
 */
public class StaticResourceUtil {

    private StaticResourceUtil() {
    }

    /**
     * 获取静态资源的绝对路径
     *
     * @param path path
     * @return absolute path
     */
    public static String getAbsolutePath(String path) {
        String absolutePath = StaticResourceUtil.class.getResource("/").getPath();
        // 去点末尾的/
        if (absolutePath.endsWith("/")) {
            absolutePath = absolutePath.substring(0, absolutePath.length() - 1);
        }
        return absolutePath.replaceAll("\\\\", "/") + path;
    }

    /**
     * 读取静态资源文件输入流，通过输出流输出
     *
     * @param inputStream  InputStream
     * @param outputStream OutputStream
     */
    public static void outputStaticResource(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] bytes = new byte[1024];
        StringBuilder stringBuilder = new StringBuilder();
        while (inputStream.read(bytes) != -1) {
            stringBuilder.append(new String(bytes));
        }
        String inputStr = stringBuilder.toString();
        // 输出http请求头
        outputStream.write(HttpProtocolUtil.getHttpHeader200(
                inputStr.getBytes(StandardCharsets.UTF_8).length)
                .getBytes(StandardCharsets.UTF_8));
        // 输出具体内容
        outputStream.write(inputStr.getBytes(StandardCharsets.UTF_8));
    }
}
