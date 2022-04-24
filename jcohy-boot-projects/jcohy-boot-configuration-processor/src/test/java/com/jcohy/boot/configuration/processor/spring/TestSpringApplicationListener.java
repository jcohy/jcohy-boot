package com.jcohy.boot.configuration.processor.spring;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import com.jcohy.boot.configuration.processor.annotations.SpringApplicationListener;

/**
 * 描述: .
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2022/4/24:11:33
 * @since 2022.04.0
 */
@SpringApplicationListener
public class TestSpringApplicationListener implements ApplicationListener {

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        System.out.println("ApplicationEvent......");
    }

}
