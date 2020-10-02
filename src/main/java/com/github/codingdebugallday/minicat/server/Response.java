package com.github.codingdebugallday.minicat.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import com.github.codingdebugallday.minicat.utils.HttpProtocolUtil;
import com.github.codingdebugallday.minicat.utils.StaticResourceUtil;

/**
 * <p>
 * 把返回信息封装为Response对象（根据socket.getOutputStream()进行封装）
 * </p>
 *
 * @author isaac 2020/10/01 0:56
 * @since 1.0.0
 */
public class Response {

    private OutputStream outputStream;

    public Response() {
    }

    public Response(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void output(String content) throws IOException {
        outputStream.write(content.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 根据url来获取到静态资源的绝对路径，进一步根据绝对路径读取该静态资源文件，最终通过输出流输出
     *
     * @param path url
     */
    public void outputHtml(String path) throws IOException {
        // 获取静态资源文件的绝对路径
        String absoluteResourcePath = StaticResourceUtil.getAbsolutePath(path);
        File file = new File(absoluteResourcePath);
        if (file.exists() && file.isFile()) {
            // 读取静态资源文件 输出静态资源
            StaticResourceUtil.outputStaticResource(new FileInputStream(file), outputStream);
        } else {
            // 输出404
            output(HttpProtocolUtil.getHttpHeader404());
        }
    }
}
