package hr.algebra.reversi2.Utils;

import hr.algebra.reversi2.constants.DocumentationConstants;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public class ReflectionUtils {
    public static List<String> getClassFiles() {
        try (Stream<Path> paths = Files.walk(DocumentationConstants.TARGET_PATH)) {
            return paths
                    .map(Path::toString)
                    .filter(file -> file.endsWith(".class"))
                    .filter(file -> !file.endsWith("module-info.class"))
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException("Error reading class files", e);
        }
    }

    public static Class<?> getClassFromFileName(String classFile) throws ClassNotFoundException {
        String fullyQualifiedName = getFullyQualifiedName(classFile);
        return Class.forName(fullyQualifiedName);
    }

    public static String getFullyQualifiedName(String classFile) {
        String[] classFileTokens = classFile.split("classes");
        String classFilePath = classFileTokens[1];
        String reducedClassFilePath = classFilePath.substring(1, classFilePath.lastIndexOf('.'));
        return reducedClassFilePath.replace('\\', '.');
    }

    public static String getClassName(String classFile) {
        String fullyQualifiedName = getFullyQualifiedName(classFile);
        return getSimpleName(fullyQualifiedName);
    }

    public static String getSimpleName(String fullyQualifiedName) {
        String[] parts = fullyQualifiedName.split("\\.");
        return parts[parts.length - 1];
    }

    //Variables
    public static String getModifiers(Field field) {
        StringBuilder modifiersBuilder = new StringBuilder();
        int modifiers = field.getModifiers();

        if (Modifier.isPublic(modifiers)) {
            modifiersBuilder.append("public ");
        } else if (Modifier.isPrivate(modifiers)) {
            modifiersBuilder.append("private ");
        } else if (Modifier.isProtected(modifiers)) {
            modifiersBuilder.append("protected ");
        }

        if (Modifier.isStatic(modifiers)) {
            modifiersBuilder.append("static ");
        }

        if (Modifier.isFinal(modifiers)) {
            modifiersBuilder.append("final ");
        }

        return modifiersBuilder.toString();
    }

    public static String getMethodDetails(Method method) {
        StringBuilder methodDetails = new StringBuilder();

        appendAnnotations(method, methodDetails);

        methodDetails.append(Modifier.toString(method.getModifiers())).append(" ");

        methodDetails.append(method.getReturnType().getSimpleName()).append(" ");

        methodDetails.append(method.getName());

        appendExceptions(method,methodDetails);

        // method parameters
        methodDetails.append("(");
        Class<?>[] parameterTypes = method.getParameterTypes();
        for (int i = 0; i < parameterTypes.length; i++) {
            if (i > 0) {
                methodDetails.append(", ");
            }
            methodDetails.append(parameterTypes[i].getSimpleName());
        }
        methodDetails.append(")");

        return methodDetails.toString();
    }

    public static String getConstructorDetails(Constructor<?> constructor) {
        StringBuilder constructorDetails = new StringBuilder();

        appendAnnotations(constructor, constructorDetails);

        constructorDetails.append(Modifier.toString(constructor.getModifiers())).append(" ");

        constructorDetails.append(constructor.getDeclaringClass().getSimpleName());

        constructorDetails.append("(");
        Class<?>[] parameterTypes = constructor.getParameterTypes();
        for (int i = 0; i < parameterTypes.length; i++) {
            if (i > 0) {
                constructorDetails.append(", ");
            }
            constructorDetails.append(parameterTypes[i].getSimpleName());
        }
        constructorDetails.append(")");

        return constructorDetails.toString();
    }

    //https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Executable.html
    private static void appendAnnotations(Executable executable, StringBuilder details) {
        Annotation[] annotations = executable.getDeclaredAnnotations();
        for (Annotation annotation : annotations) {
            details.append("@").append(annotation.annotationType().getSimpleName()).append(" ");
        }
    }

    private static void appendExceptions(Executable executable, StringBuilder details) {
        Class<?>[] exceptionTypes = executable.getExceptionTypes();
        if (exceptionTypes.length > 0) {
            details.append(" throws ");
            for (int i = 0; i < exceptionTypes.length; i++) {
                if (i > 0) {
                    details.append(", ");
                }
                details.append(exceptionTypes[i].getSimpleName());
            }
        }
    }

    public static boolean isUnwantedClazz(Class<?> clazz) {
        if (clazz.isSynthetic()) {
            return true;
        }

        String name = clazz.getName();
        return name.contains("$1");
    }

    public static boolean isUnwantedField(Field field) {
        if (field.isSynthetic()) {
            return true;
        }

        String name = field.getName();
        return name.contains("$VALUES") || name.startsWith("$SwitchMap$");
    }

    public static boolean isUnwantedMethod(Method method) {
        if (method.isSynthetic()) {
            return true;
        }

        String name = method.getName();
        return name.contains("$VALUES") || name.startsWith("lambda$");
    }
}
