package com.jcohy.boot.configuration.processor.spi;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.annotation.processing.SupportedAnnotationTypes;

import com.jcohy.boot.configuration.processor.JavaSpiServiceProcessor;
import com.jcohy.boot.configuration.processor.utils.Constants;

/**
 * 描述: .
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2022/4/22:20:06
 * @since 2022.04.0
 */
@SupportedAnnotationTypes({ "com.jcohy.boot.configuration.processor.annotations.JavaSpiService" })
public class TestJavaSpiServiceProcessor extends JavaSpiServiceProcessor {

    private final File outputLocation;

    public TestJavaSpiServiceProcessor(File outputLocation) {
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
        return new File(this.outputLocation, Constants.SERVICE_RESOURCE_LOCATION + File.separator
                + "com.jcohy.boot.configuration.processor.spi.StartupService");
    }

}
