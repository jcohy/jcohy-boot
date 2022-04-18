package com.jcohy.boot.build.lock;

import org.gradle.api.Action;
import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.tasks.TaskAction;

/**
 * @author Rob Winch
 */
public class GlobalLockTask extends DefaultTask {
	@TaskAction
	public void lock() {
		Project taskProject = getProject();
		if (!taskProject.getGradle().getStartParameter().isWriteDependencyLocks()) {
			throw new IllegalStateException("You just specify --write-locks argument");
		}
		writeLocksFor(taskProject);
		taskProject.getSubprojects().forEach(this::writeLocksFor);
	}

	private void writeLocksFor(Project project) {
		project.getConfigurations().configureEach(new Action<Configuration>() {
			@Override
			public void execute(Configuration configuration) {
				if (configuration.isCanBeResolved()) {
					configuration.resolve();
				}
			}
		});
	}
}
