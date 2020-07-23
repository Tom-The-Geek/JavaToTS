package me.geek.tom.gradletools.utils.api;

import javax.annotation.Nullable;
import java.util.Map;

public class ApiMethod {

    private final String name;
    private final String returnType;
    private final Map<String, String> args;
    @Nullable
    private final String javaDoc;

    public ApiMethod(String name, String returnType, Map<String, String> args, @Nullable String javaDoc) {
        this.name = name;
        this.returnType = returnType;
        this.args = args;
        this.javaDoc = javaDoc;
    }

    public String getName() {
        return name;
    }

    public String getReturnType() {
        return returnType;
    }

    public Map<String, String> getArgs() {
        return args;
    }

    @Nullable
    public String getJavaDoc() {
        return javaDoc;
    }
}
