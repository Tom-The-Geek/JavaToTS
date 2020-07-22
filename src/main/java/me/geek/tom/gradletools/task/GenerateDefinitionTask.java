package me.geek.tom.gradletools.task;

import me.geek.tom.gradletools.JavaToTSExtension;
import me.geek.tom.gradletools.generator.TypeScriptGenerator;
import me.geek.tom.gradletools.utils.api.ApiDefinition;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.Internal;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

public class GenerateDefinitionTask extends DefaultTask {
    @TaskAction
    public void run() throws IOException {
        JavaToTSExtension extension = getExtension();
        File inputDir = extension.getApiDir();
        File outputFile = extension.getOutputFile();

        ApiDefinition definition = ApiDefinition.generate(inputDir.toPath());
        List<String> lines = TypeScriptGenerator.generateDefinition(definition);
        try (PrintStream out = new PrintStream(new FileOutputStream(outputFile))) {
            for (String ln : lines) {
                out.println(ln);
            }
        }
    }

    @Internal
    private JavaToTSExtension getExtension() {
        return getProject().getExtensions().getByType(JavaToTSExtension.class);
    }
}
