package me.geek.tom.gradletools.utils.api;

import java.util.Map;

public class ApiMethod {

    private final String name;
    private final String returnType;
    private final Map<String, String> args;

    public ApiMethod(String name, String returnType, Map<String, String> args) {
        this.name = name;
        this.returnType = returnType;
        this.args = args;
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
}
