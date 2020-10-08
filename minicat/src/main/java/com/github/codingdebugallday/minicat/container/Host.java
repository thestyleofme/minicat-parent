package com.github.codingdebugallday.minicat.container;

/**
 * <p>
 * description
 * </p>
 *
 * @author isaac 2020/10/08 19:57
 * @since 1.0.0
 */
public interface Host extends Container {

    /**
     * 设置Engine
     *
     * @param engine Engine
     */
    void setEngine(Engine engine);

    /**
     * 获取Engine
     *
     * @return Engine
     */
    Engine getEngine();

    /**
     * 设置hostName
     *
     * @param name hostName
     */
    void setName(String name);

    /**
     * 获取hostName
     *
     * @return hostName
     */
    String getName();

    /**
     * 设置appBase
     *
     * @param appBase appBase
     */
    void setAppBase(String appBase);

    /**
     * 获取appBase
     *
     * @return appBase
     */
    String getAppBase();

    /**
     * 新增Context
     *
     * @param context Context
     */
    void addContext(Context context);

    /**
     * 获取Context集合
     *
     * @return Context[]
     */
    Context[] getContextList();
}
