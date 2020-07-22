package me.geek.tom.gradletools;

import me.geek.tom.gradletools.generator.TypeScriptGenerator;
import me.geek.tom.gradletools.utils.api.ApiDefinition;

import java.io.File;
import java.io.IOException;

public class TestingMain {

    public static void main(String[] args) throws IOException {
        ApiDefinition api = ApiDefinition.generate(new File("src/test/java/").toPath());

        System.out.println(String.join("\n", TypeScriptGenerator.generateDefinition(api)));

        System.out.println("\n\nDone!");
    }

}
