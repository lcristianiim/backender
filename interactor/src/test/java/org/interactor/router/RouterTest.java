package org.interactor.router;

import org.interactor.ApplicationConfiguration;
import org.junit.jupiter.api.Test;

import java.text.MessageFormat;
import java.util.Map;
import java.util.Properties;

import static org.interactor.router.ResponseType.JSON;
import static org.junit.jupiter.api.Assertions.*;
class RouterTest {

    @Test
    public void givenPathRegisteredInProperties_ShouldReturnTheAssociatedRequestBody() {
        String apiPrefix = ApplicationConfiguration.INSTANCE.getApiPath();
        String requestPath = apiPrefix + "/cool-path";
        String body = "associated body";
        int responseCode = 200;

        Router router = new Router();
        router.setControllerResolver(this::getController);

        Properties properties = new Properties();
        properties.setProperty("cool-path", "IrrelevantInThisTestClass");
        router.setTheGETRoutes(properties);

        ReqContextDTO ctx = new ReqContextDTO();
        ctx.setRequestPath(requestPath);
        RouterResponse result = router.get(ctx);

        assertEquals(body, result.getBody());
        assertEquals(responseCode, result.getCode());
        assertEquals(JSON, result.getType());

    }

    @Test
    void givenRegisteredPathWithRequestAndQueryParams_ShouldReturnResponseProcessedByController() {
        String apiPrefix = ApplicationConfiguration.INSTANCE.getApiPath();
        String requestPath = apiPrefix + "/products/1/something/blue?name=ball&age=20";
        String body = "1,blue,ball,20";

        Router router = new Router();
        router.setControllerResolver(this::getControllerWithPathAndQueryParams);

        Properties properties = new Properties();
        properties.setProperty("products/{id}/something/{color}?name&age", "IrrelevantInThisTestClass");
        router.setTheGETRoutes(properties);

        ReqContextDTO ctx = new ReqContextDTO();
        ctx.setRequestPath(requestPath);
        RouterResponse result = router.get(ctx);

        assertEquals(body, result.getBody());
    }

    Controller getControllerWithPathAndQueryParams(String className, ReqContextDTO ctx) {
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

    Controller getController(String className, ReqContextDTO ctx) {
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