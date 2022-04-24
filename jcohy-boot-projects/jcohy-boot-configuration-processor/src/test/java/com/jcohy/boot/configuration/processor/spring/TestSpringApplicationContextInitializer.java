package com.jcohy.boot.configuration.processor.spring;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

import com.jcohy.boot.configuration.processor.annotations.JavaSpiService;
import com.jcohy.boot.configuration.processor.annotations.SpringApplicationContextInitializer;

/**
 * 描述: .
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2022/4/24:11:32
 * @since 2022.04.0
 */
@SpringApplicationContextInitializer
@JavaSpiService(TestSpringApplicationContextInitializer.class)
public class TestSpringApplicationContextInitializer implements ApplicationContextInitializer {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        System.out.println("ApplicationContextInitializer......");
    }

}
