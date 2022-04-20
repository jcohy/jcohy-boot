package com.jcohy.boot.configuration.processor.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 描述: 用于在 spring.factories 文件中生成 .
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2022/4/20:11:10
 * @since 2022.04.0
 */
@Documented
@Retention(java.lang.annotation.RetentionPolicy.SOURCE)
@Target(java.lang.annotation.ElementType.TYPE)
public @interface SpringApplicationContextInitializer {

}
