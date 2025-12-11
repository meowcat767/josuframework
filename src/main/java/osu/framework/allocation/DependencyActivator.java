package osu.framework.allocation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class DependencyActivator {

    public static void activate(Object target, DependencyContainer dependencies) {
        injectFields(target, dependencies);
        injectMethods(target, dependencies);
    }

    private static void injectFields(Object target, DependencyContainer dependencies) {
        Class<?> clazz = target.getClass();
        while (clazz != null) {
            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(Resolved.class)) {
                    Resolved annotation = field.getAnnotation(Resolved.class);
                    Object value = dependencies.get(field.getType());
                    if (value == null && !annotation.canBeNull()) {
                        throw new RuntimeException("Unable to resolve dependency for field: " + field.getName()
                                + " of type " + field.getType().getName());
                    }
                    try {
                        field.setAccessible(true);
                        field.set(target, value);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException("Failed to inject dependency into field: " + field.getName(), e);
                    }
                }
            }
            clazz = clazz.getSuperclass();
        }
    }

    private static void injectMethods(Object target, DependencyContainer dependencies) {
        Class<?> clazz = target.getClass();
        while (clazz != null) {
            for (Method method : clazz.getDeclaredMethods()) {
                if (method.isAnnotationPresent(BackgroundDependencyLoader.class)) {
                    BackgroundDependencyLoader annotation = method.getAnnotation(BackgroundDependencyLoader.class);
                    Class<?>[] parameterTypes = method.getParameterTypes();
                    Object[] parameters = new Object[parameterTypes.length];

                    for (int i = 0; i < parameterTypes.length; i++) {
                        Object value = dependencies.get(parameterTypes[i]);
                        if (value == null && !annotation.permitNulls()) {
                            throw new RuntimeException("Unable to resolve dependency for parameter " + i + " of method "
                                    + method.getName() + " of type " + parameterTypes[i].getName());
                        }
                        parameters[i] = value;
                    }

                    try {
                        method.setAccessible(true);
                        method.invoke(target, parameters);
                    } catch (Exception e) {
                        throw new RuntimeException("Failed to inject dependencies into method: " + method.getName(), e);
                    }
                }
            }
            clazz = clazz.getSuperclass();
        }
    }
}
