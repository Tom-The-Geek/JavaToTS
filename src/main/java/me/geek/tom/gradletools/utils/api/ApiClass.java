package me.geek.tom.gradletools.utils.api;

import javax.annotation.Nullable;
import java.util.List;

public class ApiClass {
    private final String name;
    private final List<ApiMethod> methods;
    @Nullable
    private final String javaDoc;

    public ApiClass(String name, List<ApiMethod> methods, @Nullable String javaDoc) {
        this.name = name;
        this.methods = methods;
        this.javaDoc = javaDoc;
    }

    public String getName() {
        return name;
    }

    public List<ApiMethod> getMethods() {
        return methods;
    }

    @Nullable
    public String getJavaDoc() {
        return javaDoc;
    }
}
