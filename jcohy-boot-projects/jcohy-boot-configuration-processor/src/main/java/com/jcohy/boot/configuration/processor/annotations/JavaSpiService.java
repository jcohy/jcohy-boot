package com.jcohy.boot.configuration.processor.annotations;

/**
 * 描述: {@link java.util.ServiceLoader} 中描述的服务提供商的提供的注解一样，此注解处理器可以自动生成 被注解的类的配置文件，然后被 *
 * {@link java.util.ServiceLoader#load(Class)} 加载.
 *
 * <p>
 * 被注解的类必须符合服务提供商规范
 * <ul>
 * <li>不能是内部类和匿名类，必须要有确定的名称
 * <li>必须要有公共的，可调用的无参构造函数
 * <li>使用这个注解的类必须要实现value参数定义的接口 { @code value()}
 * </ul>
 *
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2022/4/20:11:11
 * @since 2022.04.0
 */
public @interface JavaSpiService {

}
