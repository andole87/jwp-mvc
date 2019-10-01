package nextstep.mvc.tobe;

import nextstep.mvc.asis.Controller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slipp.controller.CreateUserController;
import slipp.controller.HomeController;
import slipp.controller.ProfileController;

import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ComponentScannerTest {
    private ComponentScanner scanner;

    @BeforeEach
    void setUp() {
        scanner = new ComponentScanner("slipp.controller", false);
    }

    @Test
    void 타입을_기준으로_스캔() {
        Set<Class<? extends Controller>> controllers = scanner.scanClassesByType(Controller.class);

        assertThat(controllers).isNotNull();
        assertThat(controllers.contains(CreateUserController.class)).isTrue();
        assertThat(controllers.contains(HomeController.class)).isTrue();
    }

    @Test
    void 어노태이션_기준으로_스캔() {
        Set<Class<?>> classes = scanner.scanClassesByAnnotation(nextstep.web.annotation.Controller.class);

        assertThat(classes).isNotNull();
        assertThat(classes.contains(CreateUserController.class)).isTrue();
        assertThat(classes.contains(HomeController.class)).isFalse();
    }

    @Test
    void 타입과_어노테이션_기준으로_스캔() {
        Set<Class<? extends Controller>> controllers = scanner.scanClassesByTypeAndAnnotation(Controller.class, nextstep.web.annotation.Controller.class);

        assertThat(controllers).isNotNull();
        assertThat(controllers.contains(CreateUserController.class)).isTrue();
        assertThat(controllers.contains(ProfileController.class)).isTrue();
        assertThat(controllers.size()).isEqualTo(2);
    }
}