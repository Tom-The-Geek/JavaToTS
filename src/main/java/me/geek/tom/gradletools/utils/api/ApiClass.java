package me.geek.tom.gradletools.utils.api;

import java.util.List;

public class ApiClass {
    private final String name;
    private final List<ApiMethod> methods;

    public ApiClass(String name, List<ApiMethod> methods) {
        this.name = name;
        this.methods = methods;
    }

    public String getName() {
        return name;
    }

    public List<ApiMethod> getMethods() {
        return methods;
    }
}
