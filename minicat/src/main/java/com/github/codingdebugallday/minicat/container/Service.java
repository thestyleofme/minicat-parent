package com.github.codingdebugallday.minicat.container;

/**
 * <p>
 * description
 * </p>
 *
 * @author isaac 2020/10/08 19:46
 * @since 1.0.0
 */
public interface Service extends Container {

    /**
     * setServer
     *
     * @param server Server
     */
    void setServer(Server server);

    /**
     * 获取引擎列表
     *
     * @return Engine[]
     */
    Engine[] getEngines();

    /**
     * 新增Engine
     *
     * @param engine Engine
     */
    void addEngine(Engine engine);
}
