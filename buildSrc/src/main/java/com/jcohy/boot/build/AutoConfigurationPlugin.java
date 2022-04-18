package com.jcohy.boot.build;

import java.util.Collections;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.JavaPlugin;

import com.jcohy.convention.JcohyVersion;

/**
 * 描述: .
 *
 * <p>
 * Copyright © 2022 <a href="https://www.xuanwuai.com" target= "_blank">https://www.xuanwuai.com</a>
 *
 * @author jiac
 * @version 2022.04.0 2022/04/15:17:38
 * @since 2022.04.0
 */
public class AutoConfigurationPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        project.getPlugins().withType(JavaPlugin.class, javaPlugin -> {
            project.getDependencies().add(JavaPlugin.ANNOTATION_PROCESSOR_CONFIGURATION_NAME, "org.springframework.boot:spring-boot-autoconfigure-processor:" + JcohyVersion.getSpringBootVersion());
            project.getDependencies().add(JavaPlugin.ANNOTATION_PROCESSOR_CONFIGURATION_NAME, "org.springframework.boot:spring-boot-configuration-processor:" + JcohyVersion.getSpringBootVersion());
            project.getDependencies().add(JavaPlugin.ANNOTATION_PROCESSOR_CONFIGURATION_NAME, project.getDependencies().project(Collections.singletonMap("path",
                    ":jcohy-boot-projects:jcohy-boot-configuration-processor")));
        });
    }
}
