package com.jcohy.boot.build;

import java.util.HashMap;
import java.util.Map;

import org.asciidoctor.gradle.jvm.AbstractAsciidoctorTask;
import org.asciidoctor.gradle.jvm.AsciidoctorJPlugin;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.PluginContainer;

import com.jcohy.convention.conventions.ConventionsPlugin;
import com.jcohy.convention.deployed.DeployedPlugin;

/**
 * 描述: .
 *
 * <p>
 * Copyright © 2022 <a href="https://www.xuanwuai.com" target= "_blank">https://www.xuanwuai.com</a>
 *
 * @author jiac
 * @version 2022.04.0 2022/04/15:12:57
 * @since 2022.04.0
 */
public class AsciidoctorDocsPlugins implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        PluginContainer plugins = project.getPlugins();
        plugins.apply(AsciidoctorJPlugin.class);
        plugins.apply(ConventionsPlugin.class);
        plugins.apply(DeployedPlugin.class);
        plugins.withType(AsciidoctorJPlugin.class,(asciidoctorPlugin) -> {
            project.getTasks().withType(AbstractAsciidoctorTask.class, (asciidoctorTask) -> {
                configureAsciidoctorTask(project, asciidoctorTask);
            });
        });
    }

    private void configureAsciidoctorTask(Project project, AbstractAsciidoctorTask asciidoctorTask) {
        configureCommonAttributes(project, asciidoctorTask);
    }

    private void configureCommonAttributes(Project project, AbstractAsciidoctorTask asciidoctorTask) {
        Map<String, Object> attributes = new HashMap<>();
        addAsciidoctorTaskAttributes(project,attributes);
        asciidoctorTask.attributes(attributes);
    }

    private void addAsciidoctorTaskAttributes(Project project,Map<String, Object> attributes) {
        attributes.put("rootProject", project.getRootProject().getProjectDir());
    }
}
