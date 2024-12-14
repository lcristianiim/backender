package org.interactor.controllers;

import org.interactor.modules.router.RouterService;
import org.interactor.modules.router.dtos.Controller;
import org.interactor.modules.router.dtos.ReqContextDTO;
import org.interactor.modules.router.dtos.RouterResponse;

import java.text.MessageFormat;
import java.util.Map;

import static org.interactor.modules.router.dtos.ResponseType.JSON;

public class TestController implements Controller {
    private String id;
    private String name;
    private String age;

    @Override
    public RouterResponse getResponse() {
        RouterResponse response = new RouterResponse();
        String body = MessageFormat.format("id: {0}, name: {1}, age: {2}", id, name, age);
        response.setBody(body);
        response.setCode(200);
        response.setType(JSON);
        return response;
    }

    @Override
    public void initialize(ReqContextDTO controllerData, String registeredPath) {

        Map<String, String> pathParams = RouterService.INSTANCE.getRouter()
                .getPathParams(registeredPath, controllerData.getRequestPath());

        Map<String, String> queryParams = RouterService.INSTANCE.getRouter()
                .getQueryParams(registeredPath, controllerData.getRequestPath());

        id = pathParams.get("id");
        name = queryParams.get("name");
        age = queryParams.get("age");
    }
}
