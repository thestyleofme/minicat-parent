package com.github.codingdebugallday.minicat.utils;

import java.io.InputStream;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;

/**
 * <p>
 * description
 * </p>
 *
 * @author isaac 2020/10/08 19:33
 * @since 1.0.0
 */
public class ServerXmlResource {

    private static final String SERVER = "server.xml";

    private static volatile Document serverDocument;

    private ServerXmlResource() {
        throw new IllegalStateException("Utility class");
    }

    public static Document getServerDocument() {
        if (serverDocument == null) {
            synchronized (ServerXmlResource.class) {
                try {
                    InputStream resourceAsStream = ServerXmlResource.class.getClassLoader().getResourceAsStream(SERVER);
                    SAXReader saxReader = new SAXReader();
                    serverDocument = saxReader.read(resourceAsStream);
                } catch (Exception e) {
                    e.printStackTrace();
                    serverDocument = null;
                }
            }
        }
        return serverDocument;
    }

}
