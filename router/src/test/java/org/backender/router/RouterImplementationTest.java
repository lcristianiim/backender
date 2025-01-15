package org.backender.router;

import org.interactor.internals.Route;
import org.interactor.modules.router.dtos.Controller;
import org.interactor.modules.router.dtos.InteractorRequest;
import org.interactor.modules.router.dtos.InteractorResponse;
import org.interactor.modules.router.dtos.RequestType;
import org.junit.jupiter.api.Test;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import static org.interactor.configuration.RegisteredRoute.setCustomRoutesForTests;
import static org.interactor.modules.router.dtos.ResponseType.JSON;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RouterImplementationTest {

    @Test
    public void givenPathRegisteredInProperties_ShouldReturnTheAssociatedRequestBody() {
        String requestPath = "cool-path";
        String body = "associated body";
        int responseCode = 200;

        RouterImplementation routerImplementation = new RouterImplementation();

        setCustomRoutesForTests(List.of(
                new Route(RequestType.GET, requestPath, getController(), List.of())
        ));

        InteractorRequest ctx = new InteractorRequest();
        ctx.setRequestPath(requestPath);
        ctx.setRequestType(RequestType.GET);
        InteractorResponse result = routerImplementation.processRequest(ctx);

        assertEquals(body, result.getBody());
        assertEquals(responseCode, result.getCode());
        assertEquals(JSON, result.getType());

    }

    @Test
    void givenRegisteredPathWithRequestAndQueryParams_ShouldReturnResponseProcessedByController() {
        String requestPath = "products/1/something/blue?name=ball&age=20";
        String controllerPath = "products/{id}/something/{color}?name&age";
        String body = "1,blue,ball,20";

        RouterImplementation routerImplementation = new RouterImplementation();

        setCustomRoutesForTests(List.of(
                new Route(RequestType.GET, controllerPath, getControllerWithPathAndQueryParams(), List.of())
        ));

        InteractorRequest ctx = new InteractorRequest();
        ctx.setRequestPath(requestPath);
        ctx.setRequestType(RequestType.GET);
        InteractorResponse result = routerImplementation.processRequest(ctx);

        assertEquals(body, result.getBody());
    }

    Controller getControllerWithPathAndQueryParams() {
        return new Controller() {
            private String id;
            private String color;
            private String name;
            private String age;


            @Override
            public InteractorResponse getResponse() {
                InteractorResponse interactorResponse = new InteractorResponse();
                interactorResponse.setType(JSON);
                interactorResponse.setCode(200);
                interactorResponse.setBody(MessageFormat.format("{0},{1},{2},{3}", id, color, name, age));
                return interactorResponse;
            }

            @Override
            public void initialize(InteractorRequest controllerData, Route registeredPath) {
                PathCommonOperations operations = new PathCommonOperations();

                Map<String, String> pathParams = operations.getPathParams(
                        registeredPath.path(), controllerData.getRequestPath());

                Map<String, String> queryParams = operations.getQueryParams(
                        registeredPath.path(), controllerData.getRequestPath());

                id = pathParams.get("id");
                color = pathParams.get("color");
                name = queryParams.get("name");
                age = queryParams.get("age");
            }
        };
    }

    Controller getController() {
        return new Controller() {

            @Override
            public InteractorResponse getResponse() {
                InteractorResponse interactorResponse = new InteractorResponse();
                interactorResponse.setType(JSON);
                interactorResponse.setCode(200);
                interactorResponse.setBody("associated body");
                return interactorResponse;
            }

            @Override
            public void initialize(InteractorRequest controllerData, Route route) {
            }
        };
    }

}