package org.interactor.controllers;

import org.interactor.configuration.Route;
import org.interactor.modules.router.RouterService;
import org.interactor.modules.router.dtos.Controller;
import org.interactor.modules.router.dtos.InteractorRequest;
import org.interactor.modules.router.dtos.InteractorResponse;

import java.text.MessageFormat;
import java.util.Map;

import static org.interactor.modules.router.dtos.ResponseType.JSON;

public class TestController implements Controller {
    private String id;
    private String name;
    private String age;

    @Override
    public InteractorResponse getResponse() {
        InteractorResponse response = new InteractorResponse();
        String body = MessageFormat.format("id: {0}, name: {1}, age: {2}", id, name, age);
        response.setBody(body);
        response.setCode(200);
        response.setType(JSON);
        return response;
    }

    @Override
    public void initialize(InteractorRequest controllerData, Route registeredRoute) {

        Map<String, String> pathParams = RouterService.INSTANCE.getRouter()
                .getPathParams(registeredRoute.path(), controllerData.getRequestPath());

        Map<String, String> queryParams = RouterService.INSTANCE.getRouter()
                .getQueryParams(registeredRoute.path(), controllerData.getRequestPath());

        id = pathParams.get("id");
        name = queryParams.get("name");
        age = queryParams.get("age");
    }
}
