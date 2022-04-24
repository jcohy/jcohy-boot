package com.jcohy.boot.configuration.processor.spi;

/**
 * 描述: .
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2022/4/22:20:04
 * @since 2022.04.0
 */
public interface StartupService {

    /**
     * 启动时加载 spi.
     */
    void startup();

}
