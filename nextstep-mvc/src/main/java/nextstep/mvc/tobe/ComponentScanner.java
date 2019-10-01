package nextstep.mvc.tobe;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import java.lang.annotation.Annotation;
import java.util.Set;
import java.util.stream.Collectors;

public class ComponentScanner {
    private Reflections reflections;

    public ComponentScanner(String basePackage, boolean hasExclude) {
        reflections = new Reflections(basePackage, new SubTypesScanner(hasExclude));
    }

    public <T> Set<Class<? extends T>> scanClassesByType(Class<T> type) {
        return reflections.getSubTypesOf(type);
    }


    public <T> Set<Class<? extends T>> scanClassesByTypeAndAnnotation(Class<T> type, Class<? extends Annotation> annotation) {
        return reflections.getSubTypesOf(type).stream()
                .filter(clazz -> clazz.isAnnotationPresent(annotation))
                .collect(Collectors.toSet());
    }

    public Set<Class<?>> scanClassesByAnnotation(Class<? extends Annotation> annotation) {
        return reflections.getSubTypesOf(Object.class).stream()
                .filter(clazz -> clazz.isAnnotationPresent(annotation))
                .collect(Collectors.toSet());
    }
}
