package com.jcohy.boot.configuration.processor.spring;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.annotation.processing.SupportedAnnotationTypes;

import com.jcohy.boot.configuration.processor.SpringFactoriesProcessor;
import com.jcohy.boot.configuration.processor.utils.Constants;

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
@SupportedAnnotationTypes({ "*" })
public class TestSpringFactoryProcessor extends SpringFactoriesProcessor {

    private final File outputLocation;

    public TestSpringFactoryProcessor(File outputLocation) {
        this.outputLocation = outputLocation;
    }

    public Properties getWrittenProperties() throws IOException {
        File file = getWrittenFile();
        if (!file.exists()) {
            return null;
        }
        try (FileInputStream inputStream = new FileInputStream(file)) {
            Properties properties = new Properties();
            properties.load(inputStream);
            return properties;
        }
    }

    public File getWrittenFile() {
        return new File(this.outputLocation, Constants.SPRING_FACTORY_RESOURCE_LOCATION);
    }

}
