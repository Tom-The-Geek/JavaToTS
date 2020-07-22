package me.geek.tom.gradletools;

import org.gradle.api.Project;

import java.io.File;

public class JavaToTSExtension {

    private File apiDir = null;
    private File outputFile = null;

    public JavaToTSExtension(Project project) {

    }

    public File getApiDir() {
        return apiDir;
    }

    public void setApiDir(File apiDir) {
        this.apiDir = apiDir;
    }

    public boolean isSetup() {
        return apiDir != null && outputFile != null;
    }

    public File getOutputFile() {
        return outputFile;
    }

    public void setOutputFile(File outputFile) {
        this.outputFile = outputFile;
    }
}
