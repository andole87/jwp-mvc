package reflection;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    public void showClass() {
        Class<Question> clazz = Question.class;
        logger.debug("##### {} Fields #####", clazz.getName());
        Arrays.stream(clazz.getDeclaredFields())
                .map(field -> String.format("%s %s", field.getType().getName(), field.getName()))
                .forEach(logger::debug);
        breakConsoleBlock();

        logger.debug("##### {} Constructors #####", clazz.getName());
        Arrays.stream(clazz.getDeclaredConstructors())
                .map(constructor -> String.format("%s %s", getAccessModifier(constructor), getParameterNames(constructor)))
                .forEach(logger::debug);
        breakConsoleBlock();

        logger.debug("##### {} Methods #####", clazz.getName());
        Arrays.stream(clazz.getDeclaredMethods())
                .map(method -> String.format("%s %s %s %s",
                        getAccessModifier(method), getReturnType(method),
                        method.getName(), getParameterNames(method)))
                .forEach(logger::debug);
    }

    private void breakConsoleBlock() {
        logger.debug("------------------------------------------------------------------------------------------------");
    }

    private AccessModifier getAccessModifier(Constructor<?> constructor) {
        return AccessModifier.of(constructor.getModifiers());
    }

    private AccessModifier getAccessModifier(Method method) {
        return AccessModifier.of(method.getModifiers());
    }

    private List<String> getParameterNames(Method method) {
        return Arrays.stream(method.getParameterTypes())
                .map(Class::getName)
                .collect(Collectors.toList());
    }

    private List<String> getParameterNames(Constructor<?> constructor) {
        return Arrays.stream(constructor.getParameterTypes())
                .map(Class::getName)
                .collect(Collectors.toList());
    }

    private String getReturnType(Method method) {
        return method.getReturnType().getName();
    }

    @Test
    @SuppressWarnings("rawtypes")
    public void constructor_with_args() throws Exception {
        Class<Question> clazz = Question.class;
        Constructor[] constructors = clazz.getConstructors();
        for (Constructor constructor : constructors) {
            Class[] parameterTypes = constructor.getParameterTypes();
            logger.debug("paramer length : {}", parameterTypes.length);
            for (Class paramType : parameterTypes) {
                logger.debug("param type : {}", paramType);
            }

            if (parameterTypes.length == 3) {
                Object object = constructor.newInstance("writer", "title", "contents");
                logger.debug(object.toString());

                assertThat(object instanceof Question).isTrue();
                assertDoesNotThrow(() -> (Question) object);

                Question question = (Question) object;

                assertThat(question.getWriter()).isEqualTo("writer");
                assertThat(question.getTitle()).isEqualTo("title");
                assertThat(question.getContents()).isEqualTo("contents");
            } else if (parameterTypes.length == 6) {
                Object object = constructor.newInstance(1L, "writer", "title", "contents", new Date(), 0);
                logger.debug(object.toString());

                assertThat(object instanceof Question).isTrue();
                assertDoesNotThrow(() -> (Question) object);

                Question question = (Question) object;

                assertThat(question.getQuestionId()).isEqualTo(1L);
                assertThat(question.getWriter()).isEqualTo("writer");
                assertThat(question.getTitle()).isEqualTo("title");
                assertThat(question.getContents()).isEqualTo("contents");
                assertThat(question.getCountOfComment()).isEqualTo(0);
            }
        }
    }

    @Test
    public void privateFieldAccess() {
        Class<Student> clazz = Student.class;
        logger.debug(clazz.getName());

        // TODO Student private field에 값을 저장하고 조회한다.
    }

    public static enum AccessModifier {
        PACKAGE_PRIVATE(0),
        PUBLIC(1),
        PRIVATE(2),
        PROTECTED(4);

        private int modifier;

        AccessModifier(int modifier) {
            this.modifier = modifier;
        }

        public static AccessModifier of(int modifier) {
            return Arrays.stream(AccessModifier.values())
                    .filter(element -> element.modifier == modifier)
                    .findAny()
                    .orElseThrow(IllegalArgumentException::new);
        }

        @Override
        public String toString() {
            return this.name();
        }
    }
}
