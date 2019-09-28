package nextstep.mvc.tobe;

import org.junit.jupiter.api.Test;

class AndoleTest {

    @Test
    void name() {
        AnnotationHandlerMapping am = new AnnotationHandlerMapping("slipp.controller");
        am.initialize();
    }
}