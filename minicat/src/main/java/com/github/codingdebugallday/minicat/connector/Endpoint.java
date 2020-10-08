package com.github.codingdebugallday.minicat.connector;

import java.io.IOException;

/**
 * <p>
 * description
 * </p>
 *
 * @author isaac 2020/10/08 19:52
 * @since 1.0.0
 */
public interface Endpoint {

    /**
     * 设置ProtocolHandler
     *
     * @param protocolHandler ProtocolHandler
     */
    void setProtocolHandler(ProtocolHandler protocolHandler);

    /**
     * init
     *
     * @throws IOException IOException
     */
    void init() throws IOException;

    /**
     * start
     *
     * @throws IOException IOException
     */
    void start() throws IOException;

    /**
     * bind
     *
     * @throws IOException IOException
     */
    void bind() throws IOException;
}
