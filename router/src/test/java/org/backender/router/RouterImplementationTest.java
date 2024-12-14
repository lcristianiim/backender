package org.backender.router;

import org.interactor.ApplicationConfiguration;
import org.interactor.modules.router.dtos.Controller;
import org.interactor.modules.router.dtos.ReqContextDTO;
import org.interactor.modules.router.dtos.RouterResponse;
import org.junit.jupiter.api.Test;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import static org.interactor.modules.router.dtos.ResponseType.JSON;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RouterImplementationTest {

    @Test
    public void givenPathRegisteredInProperties_ShouldReturnTheAssociatedRequestBody() {
        String apiPrefix = ApplicationConfiguration.INSTANCE.getApiPath();
        String requestPath = apiPrefix + "/cool-path";
        String body = "associated body";
        int responseCode = 200;


        RouterImplementation routerImplementation = new RouterImplementation();

        Map<String, Controller> getRoutes = new HashMap<>();
        getRoutes.put("cool-path", getController());
        routerImplementation.setGetRoutes(getRoutes);

        ReqContextDTO ctx = new ReqContextDTO();
        ctx.setRequestPath(requestPath);
        RouterResponse result = routerImplementation.get(ctx);

        assertEquals(body, result.getBody());
        assertEquals(responseCode, result.getCode());
        assertEquals(JSON, result.getType());

    }

    @Test
    void givenRegisteredPathWithRequestAndQueryParams_ShouldReturnResponseProcessedByController() {
        String apiPrefix = ApplicationConfiguration.INSTANCE.getApiPath();
        String requestPath = apiPrefix + "/products/1/something/blue?name=ball&age=20";
        String body = "1,blue,ball,20";

        RouterImplementation routerImplementation = new RouterImplementation();

        Map<String, Controller> getRoutes = new HashMap<>();
        getRoutes.put("products/{id}/something/{color}?name&age", getControllerWithPathAndQueryParams());
        routerImplementation.setGetRoutes(getRoutes);

        ReqContextDTO ctx = new ReqContextDTO();
        ctx.setRequestPath(requestPath);
        RouterResponse result = routerImplementation.get(ctx);

        assertEquals(body, result.getBody());
    }

    Controller getControllerWithPathAndQueryParams() {
        return new Controller() {
            private String id;
            private String color;
            private String name;
            private String age;


            @Override
            public RouterResponse getResponse() {
                RouterResponse routerResponse = new RouterResponse();
                routerResponse.setType(JSON);
                routerResponse.setCode(200);
                routerResponse.setBody(MessageFormat.format("{0},{1},{2},{3}", id, color, name, age));
                return routerResponse;
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
                color = pathParams.get("color");
                name = queryParams.get("name");
                age = queryParams.get("age");
            }
        };
    }

    Controller getController() {
        return new Controller() {

            @Override
            public RouterResponse getResponse() {
                RouterResponse routerResponse = new RouterResponse();
                routerResponse.setType(JSON);
                routerResponse.setCode(200);
                routerResponse.setBody("associated body");
                return routerResponse;
            }

            @Override
            public void initialize(ReqContextDTO controllerData, String registeredPath) {
            }
        };
    }

}