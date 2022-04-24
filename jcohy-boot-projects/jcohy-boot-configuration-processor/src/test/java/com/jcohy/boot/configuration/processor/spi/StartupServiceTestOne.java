package com.jcohy.boot.configuration.processor.spi;

import com.jcohy.boot.configuration.processor.annotations.JavaSpiService;

/**
 * 描述: .
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2022/4/22:20:05
 * @since 2022.04.0
 */
@JavaSpiService(value = StartupService.class, name = "StartupServiceTestOne")
public class StartupServiceTestOne implements StartupService {

    @Override
    public void startup() {

    }

}
