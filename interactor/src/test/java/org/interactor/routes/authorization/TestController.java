package org.interactor.routes.authorization;

import org.interactor.internals.Route;
import org.interactor.modules.router.dtos.Controller;
import org.interactor.modules.router.dtos.InteractorRequest;
import org.interactor.modules.router.dtos.InteractorResponse;

public class TestController implements Controller {
    @Override
    public InteractorResponse getResponse() {
        InteractorResponse result = new InteractorResponse();
        result.setCode(111);
        return result;
    }

    @Override
    public void initialize(InteractorRequest controllerData, Route registeredPath) {

    }
}
