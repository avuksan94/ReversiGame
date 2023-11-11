package hr.algebra.reversi2.documentation;

import hr.algebra.reversi2.Utils.ReflectionUtils;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

//https://refactoring.guru/design-patterns/adapter/java/example

public class HtmlDocumentationGenerator{
    public HtmlDocumentationGenerator() {
    }

    private static void addHtmlHeader(StringBuilder html) {
        html.append("""
            <!DOCTYPE html>
            <html>
            <head>
            <title>HTML Document Generator</title>
            </head>
            <body>
            """);
    }

    private static void appendClassDocumentation(StringBuilder html, String classFile) {
        String className = ReflectionUtils.getClassName(classFile);
        if (className.contains("$1")){
            return;
        }
        html.append("<h2 style=\"color: red;\">").append(className).append("</h2>");
        try {
            Class<?> deserializedClass = ReflectionUtils.getClassFromFileName(classFile);
            //***************************************************************
            if (deserializedClass.getDeclaredFields().length != 0){
                html.append("<h4>");
                html.append("FIELDS: ");
                html.append("</h4>");
            }
            for (Field field : deserializedClass.getDeclaredFields()) {
                addFieldDocumentation(html, field);
            }
            //***************************************************************
            if(deserializedClass.getDeclaredMethods().length != 0){
                html.append("<h4>");
                html.append("METHODS: ");
                html.append("</h4>");
            }
            for (Method method : deserializedClass.getDeclaredMethods()) {
                addMethodDocumentation(html, method);
            }
            //***************************************************************
            if (deserializedClass.getDeclaredConstructors().length != 0) {
                html.append("<h4>");
                html.append("CONSTRUCTORS: ");
                html.append("</h4>");
            }
            for (Constructor<?>  constructor : deserializedClass.getDeclaredConstructors()) {
                addConstructorDocumentation(html, constructor);
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Error loading class " + className, e);
        }
    }

    public static void addFieldDocumentation(StringBuilder html, Field field) {
        if (ReflectionUtils.isUnwantedField(field)) {
            return; // I don't want $VALUES and $SwitchMap$
        }

        html.append("<p>");
        String modifiers = ReflectionUtils.getModifiers(field);
        html.append(modifiers)
                .append(ReflectionUtils.getSimpleName(field.getType().getTypeName()))
                .append(" ")
                .append(field.getName())
                .append("</p>");
    }

    public static void addMethodDocumentation(StringBuilder html, Method method) {
        if (ReflectionUtils.isUnwantedMethod(method)){
            return;
        }
        html.append("<p>");
        String methods = ReflectionUtils.getMethodDetails(method);
        html.append(methods)
                .append("</p>");
    }

    public static void addConstructorDocumentation(StringBuilder html, Constructor<?> constructor) {
        html.append("<p>");
        String constructors = ReflectionUtils.getConstructorDetails(constructor);
        html.append(constructors)
                .append("</p>");
    }

    private static void addHtmlFooter(StringBuilder html) {
        html.append("""
            </body>
            </html>
            """);
    }

    //custom file saving
    public void writeHtmlToFile(String html, Path path) {
        try {
            Files.createDirectories(path.getParent());
            Files.write(path, html.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Error writing documentation file", e);
        }
    }

    public String generateHtmlContent() {
        List<String> classFiles = ReflectionUtils.getClassFiles();

        StringBuilder html = new StringBuilder();
        addHtmlHeader(html);

        for (String classFile : classFiles) {
            appendClassDocumentation(html, classFile);
        }

        addHtmlFooter(html);

        return html.toString();
    }

}