package me.geek.tom.gradletools.utils;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.comments.JavadocComment;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import me.geek.tom.gradletools.utils.api.ApiClass;
import me.geek.tom.gradletools.utils.api.ApiMethod;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class JavaInputReader extends VoidVisitorAdapter<Void> {

    private final List<ApiMethod> methods = new ArrayList<>();
    private String name;
    private boolean hasMethods;
    private String javaDoc;

    public static Optional<ApiClass> visitClass(Path path, List<ApiMethod> globals) throws IOException {
        File file = path.toFile();

        CompilationUnit cu = StaticJavaParser.parse(file);
        JavaInputReader reader = new JavaInputReader();
        reader.visit(cu, null);

        List<ApiMethod> methods = reader.methods;

        if (reader.hasMethods) {
            ApiClass cls = new ApiClass(
                    reader.name,
                    methods,
                    reader.javaDoc);
            return Optional.of(cls);
        } else {
            globals.addAll(methods);
            return Optional.empty();
        }
    }

    @Override
    public void visit(ClassOrInterfaceDeclaration n, Void arg) {
        name = n.getNameAsString();
        hasMethods = true;

        Optional<JavadocComment> optionalJavadocComment = n.getJavadocComment();
        javaDoc = optionalJavadocComment.map(Comment::getContent).orElse(null);

        super.visit(n, arg);
    }

    @Override
    public void visit(MethodDeclaration md, Void arg) {
        super.visit(md, arg);
        //System.out.println("Visit method " + md.getNameAsString());

        Map<String, String> params = new HashMap<>();
        NodeList<Parameter> parameters = md.getParameters();
        if (!parameters.isEmpty()) {
            for (Parameter param : parameters) {
                params.put(param.getNameAsString(), param.getTypeAsString());
            }
        }

        Optional<JavadocComment> optionalJavadocComment = md.getJavadocComment();
        String javaDoc;
        javaDoc = optionalJavadocComment.map(Comment::getContent).orElse(null);

        if (md.getModifiers().stream().anyMatch(m -> m.getKeyword().asString().equals("static"))) {
            //System.out.println("Found static method " + md.getNameAsString() + ". Adding to list!");
            methods.add(
                    new ApiMethod(
                            md.getNameAsString(),
                            md.getTypeAsString(),
                            params,
                            javaDoc
                    )
            );
            hasMethods = false;
        }
        if (md.getModifiers().stream().noneMatch(m -> m.getKeyword().asString().equals("static")) && hasMethods) {
            //System.out.println("Found method " + md.getNameAsString() + ". Adding to list!");
            methods.add(
                    new ApiMethod(
                            md.getNameAsString(),
                            md.getTypeAsString(),
                            params,
                            javaDoc
                    )
            );
        }
    }
}
