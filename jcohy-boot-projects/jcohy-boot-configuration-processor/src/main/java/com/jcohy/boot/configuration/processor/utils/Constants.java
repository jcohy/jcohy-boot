package com.jcohy.boot.configuration.processor.utils;

/**
 * 描述: 常量类.
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2022/4/20:11:14
 * @since 2022.04.0
 */
public final class Constants {

    /**
     * META-INF/services/.
     */
    public static final String SERVICE_RESOURCE_LOCATION = "META-INF/services/";

    /**
     * META-INF/spring.factories.
     */
    public static final String SPRING_FACTORY_RESOURCE_LOCATION = "META-INF/spring.factories";

    /**
     * META-INF/spring-devtools.properties.
     */
    public static final String DEVTOOLS_RESOURCE_LOCATION = "META-INF/spring-devtools.properties";

    private Constants() {
    }

}
