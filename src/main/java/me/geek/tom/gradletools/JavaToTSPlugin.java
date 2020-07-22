package me.geek.tom.gradletools;

import me.geek.tom.gradletools.task.GenerateDefinitionTask;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.tasks.TaskContainer;

public class JavaToTSPlugin implements Plugin<Project> {
    @Override
    public void apply(Project target) {
        target.getExtensions().create("typescriptgen", JavaToTSExtension.class, target);
        TaskContainer tasks = target.getTasks();

        tasks.create("generateTsDefinition", GenerateDefinitionTask.class).setGroup("javatots");

        target.afterEvaluate(project -> {
            if (!project.getExtensions().getByType(JavaToTSExtension.class).isSetup()) {
                throw new IllegalStateException("JavaToTS plugin has not been configured!");
            }
        });
    }
}
