package org.interactor.controllers;

import org.interactor.router.*;

import java.text.MessageFormat;
import java.util.Map;

import static org.interactor.router.ResponseType.JSON;

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
        PathCommonOperations operations = new PathCommonOperations();

        Map<String, String> pathParams = operations.getPathParams(
                registeredPath, PathOperations.getPathWithoutTheAPIPart()
                        .apply(controllerData.getRequestPath()));

        Map<String, String> queryParams = operations.getQueryParams(
                registeredPath, PathOperations.getPathWithoutTheAPIPart()
                        .apply(controllerData.getRequestPath()));

        id = pathParams.get("id");
        name = queryParams.get("name");
        age = queryParams.get("age");
    }
}
