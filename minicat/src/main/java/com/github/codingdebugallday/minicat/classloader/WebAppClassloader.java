package com.github.codingdebugallday.minicat.classloader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 * 加载Web的/WEB-INF/classes/下的class
 * </p>
 *
 * @author isaac 2020/10/08 20:07
 * @since 1.0.0
 */
public class WebAppClassloader extends URLClassLoader {

    private static final String CLASS_FILE_SUFFIX = ".class";

    private final Map<String, Class<?>> classMap = new ConcurrentHashMap<>();

    private final ClassLoader classLoader;

    private final ClassLoader parent;

    public WebAppClassloader(URL[] urls) {
        super(urls);
        ClassLoader p = getParent();
        if (p == null) {
            p = getSystemClassLoader();
        }
        this.parent = p;

        ClassLoader j = String.class.getClassLoader();
        if (j == null) {
            j = getSystemClassLoader();
            while (j.getParent() != null) {
                j = j.getParent();
            }
        }
        this.classLoader = j;
    }

    protected Class<?> findLoadedClass0(String name) {
        String path = binaryNameToPath(name, true);
        return classMap.get(path);
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        synchronized (getClassLoadingLock(name)) {
            Class<?> clazz;
            // 调用父类查看是否已经加载该类
            clazz = findLoadedClass(name);
            if (clazz != null) {
                if (resolve) {
                    resolveClass(clazz);
                }
                return clazz;
            }

            // 尝试从java
            String resourceName = binaryNameToPath(name, false);
            ClassLoader javaLoader = getClassLoader();
            boolean tryLoadingFromJavaLoader;
            try {
                URL url = javaLoader.getResource(resourceName);
                tryLoadingFromJavaLoader = (url != null);
            } catch (Exception t) {
                tryLoadingFromJavaLoader = true;
            }

            if (tryLoadingFromJavaLoader) {
                try {
                    clazz = javaLoader.loadClass(name);
                    if (clazz != null) {
                        if (resolve) {
                            resolveClass(clazz);
                        }
                        return clazz;
                    }
                } catch (ClassNotFoundException e) {
                    // Ignore
                }
            }

            // 调用父类的加载
            try {
                clazz = Class.forName(name, false, parent);
                return clazz;
            } catch (Exception ignored) {
                // ignore
            }

            clazz = findClass(name);
            if (clazz != null) {
                String key = binaryNameToPath(name, true);
                classMap.put(key, clazz);
                return clazz;
            }
        }
        throw new ClassNotFoundException(name);
    }

    private ClassLoader getClassLoader() {
        return this.classLoader;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String path = binaryNameToPath(name, true);
        URL[] urLs = super.getURLs();
        File classPath = null;
        for (URL url : urLs) {
            File base = null;
            try {
                base = new File(url.toURI());
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            classPath = new File(base, path);
            if (classPath.exists()) {
                break;
            }
            classPath = null;
        }
        if (classPath == null) {
            throw new ClassNotFoundException();
        }
        byte[] classBytes = loadClassBytes(classPath);
        return this.defineClass(name, classBytes, 0, classBytes.length);
    }

    private byte[] loadClassBytes(File classFile) {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             FileInputStream fis = new FileInputStream(classFile)) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = fis.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
            }
            bos.flush();
            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    private String binaryNameToPath(String binaryName, boolean withLeadingSlash) {
        // 1 for leading '/', 6 for ".class"
        StringBuilder path = new StringBuilder(7 + binaryName.length());
        if (withLeadingSlash) {
            path.append('/');
        }
        path.append(binaryName.replace('.', '/'));
        path.append(CLASS_FILE_SUFFIX);
        return path.toString();
    }

}

