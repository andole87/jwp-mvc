package nextstep.mvc.tobe;

import nextstep.mvc.asis.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HandlerExecution {
    private Controller controller;

    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (controller != null) {
            controller.execute(request, response);
            return null;
        }
        return null;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }
}
