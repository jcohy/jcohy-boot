package com.jcohy.boot.configuration.processor;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.jcohy.boot.configuration.processor.spi.StartupServiceTestOne;
import com.jcohy.boot.configuration.processor.spi.StartupServiceTestTwo;
import com.jcohy.boot.configuration.processor.spi.TestJavaSpiServiceProcessor;
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
 * @version 2022.04.0 2022/4/22:19:54
 * @since 2022.04.0
 */
public class JavaSpiServiceProcessorTest {

    @TempDir
    File tempDir;

    private TestCompiler compiler;

    @BeforeEach
    void createCompiler() throws IOException {
        this.compiler = new TestCompiler(new File("src/test/resources"));
    }

    @Test
    void annotatedClass() throws IOException {
        Properties properties = compile(StartupServiceTestOne.class, StartupServiceTestTwo.class);
        assertThat(properties).hasSize(2);
    }

    // private WebTestClient.ListBodySpec<Object> assertThat(Properties properties) {
    // return null;
    // }

    private Properties compile(Class<?>... types) throws IOException {
        return process(types).getWrittenProperties();
    }

    private TestJavaSpiServiceProcessor process(Class<?>... types) {
        TestJavaSpiServiceProcessor processor = new TestJavaSpiServiceProcessor(this.compiler.getOutputLocation());
        this.compiler.getTask(types).call(processor);
        return processor;
    }

}
