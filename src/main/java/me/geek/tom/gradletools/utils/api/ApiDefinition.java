package me.geek.tom.gradletools.utils.api;

import me.geek.tom.gradletools.utils.JavaInputReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ApiDefinition {

    private static final Logger LOGGER = LoggerFactory.getLogger("ApiWalker");

    private final List<ApiClass> classes;
    private final List<ApiMethod> globals;

    public static ApiDefinition generate(Path srcDir) throws IOException {
        FileVisitor visitor = new FileVisitor();
        Files.walkFileTree(srcDir, visitor);
        return new ApiDefinition(visitor.classes, visitor.methods);
    }

    private ApiDefinition(List<ApiClass> classes, List<ApiMethod> methods) {
        this.classes = classes;
        this.globals = methods;
    }

    public List<ApiClass> getClasses() {
        return classes;
    }

    public List<ApiMethod> getGlobals() {
        return globals;
    }

    private static class FileVisitor implements java.nio.file.FileVisitor<Path> {

        private final List<ApiClass> classes;
        private final List<ApiMethod> methods;

        private FileVisitor() {
            classes = new ArrayList<>();
            methods = new ArrayList<>();
        }

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            System.out.println("Visit file: " + file);
            if (!attrs.isRegularFile() || !file.toString().endsWith(".java")) return FileVisitResult.CONTINUE;
            Optional<ApiClass> ret = JavaInputReader.visitClass(file, methods);
            ret.ifPresent(classes::add);

            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc) {
            LOGGER.warn("Failed to visit {}: {}", file, exc);
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
            return FileVisitResult.CONTINUE;
        }
    }
}
