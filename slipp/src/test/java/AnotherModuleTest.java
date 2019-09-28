import nextstep.mvc.tobe.AnnotationHandlerMapping;
import org.junit.jupiter.api.Test;

public class AnotherModuleTest {

    @Test
    void name() {
        AnnotationHandlerMapping mapping = new AnnotationHandlerMapping("slipp.controller");
        mapping.initialize();
    }
}
