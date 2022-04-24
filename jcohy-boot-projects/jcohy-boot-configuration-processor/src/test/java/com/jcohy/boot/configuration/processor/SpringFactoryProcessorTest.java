package com.jcohy.boot.configuration.processor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import org.springframework.util.StringUtils;

import com.jcohy.boot.configuration.processor.spring.TestNoApplicationListener;
import com.jcohy.boot.configuration.processor.spring.TestSpringApplicationContextInitializer;
import com.jcohy.boot.configuration.processor.spring.TestSpringApplicationListener;
import com.jcohy.boot.configuration.processor.spring.TestSpringFactoryProcessor;
import com.jcohy.boot.test.TestCompiler;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 描述: .
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2022/4/24:11:36
 * @since 2022.04.0
 */
public class SpringFactoryProcessorTest {

    @TempDir
    File tempDir;

    private TestCompiler compiler;

    private static Map<String, List<String>> loadSpringFactories(Properties properties) {
        Map<String, List<String>> result = new HashMap<>();

        if (properties == null) {
            return result;
        }

        for (Map.Entry<?, ?> entry : properties.entrySet()) {
            String factoryTypeName = ((String) entry.getKey()).trim();
            String[] factoryImplementationNames = StringUtils
                    .commaDelimitedListToStringArray((String) entry.getValue());
            for (String factoryImplementationName : factoryImplementationNames) {
                result.computeIfAbsent(factoryTypeName, (key) -> new ArrayList<>())
                        .add(factoryImplementationName.trim());
            }
        }
        return result;
    }

    @BeforeEach
    void createCompiler() throws IOException {
        this.compiler = new TestCompiler(new File("src/test/resources"));
    }

    /**
     * 预期结果：没有实现ApplicationListener接口的类不写入到spring.factories中.
     * TestSagaApplicationListener.class,TestSagaApplicationContextInitializer.class,
     * @throws IOException
     */
    @Test
    void annotatedClass() throws IOException {
        Properties properties = compile(TestSpringApplicationListener.class,
                TestSpringApplicationContextInitializer.class, TestNoApplicationListener.class);
        Map<String, List<String>> stringListMap = loadSpringFactories(properties);
        assertThat(stringListMap).hasSize(2);
    }

    private Properties compile(Class<?>... types) throws IOException {
        return process(types).getWrittenProperties();
    }

    private TestSpringFactoryProcessor process(Class<?>... types) {
        TestSpringFactoryProcessor processor = new TestSpringFactoryProcessor(this.compiler.getOutputLocation());
        this.compiler.getTask(types).call(processor);
        return processor;
    }

}
