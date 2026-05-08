package osu.framework.development;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

final class ReflectionUtils {

    private ReflectionUtils() {
    }

    /**
     * @return  all methods declared by the specified fixture type
     * that have the specified annotation.
     *
     * Base class methods are returned before derived class methods.
     */
    static Method[] getMethodsWithAttribute(
            Class<?> fixtureType,
            Class<? extends Annotation> annotationType,
            boolean inherit
    ) {
        if (!inherit) {
            return Arrays.stream(fixtureType.getDeclaredMethods())
                    .filter(method -> method.isAnnotationPresent(annotationType))
                    .toArray(Method[]::new);
        }

        Map<Class<?>, List<Method>> methodsByDeclaringType =
                Arrays.stream(fixtureType.getMethods())
                        .filter(method -> method.isAnnotationPresent(annotationType))
                        .collect(Collectors.groupingBy(Method::getDeclaringClass));

        List<Class<?>> hierarchy = enumerateBaseTypes(fixtureType);
        Collections.reverse(hierarchy);

        List<Method> result = new ArrayList<>();

        for (Class<?> declaringType : hierarchy) {
            List<Method> methods = methodsByDeclaringType.get(declaringType);

            if (methods != null) {
                result.addAll(methods);
            }
        }

        return result.toArray(Method[]::new);
    }

    /**
     * Enumerates a class and all base classes.
     */
    private static List<Class<?>> enumerateBaseTypes(Class<?> type) {
        List<Class<?>> types = new ArrayList<>();

        while (type != null && type != Object.class) {
            types.add(type);
            type = type.getSuperclass();
        }

        return types;
    }
}