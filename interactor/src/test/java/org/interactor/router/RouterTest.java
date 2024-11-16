package org.interactor.router;

import org.interactor.ApplicationConfiguration;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.interactor.router.ResponseType.JSON;
import static org.junit.jupiter.api.Assertions.*;
class RouterTest {

    @Test
    public void givenPathRegisteredInProperties_ShouldReturnTheAssociatedRequestBody() {
        String apiPrefix = ApplicationConfiguration.INSTANCE.getApiPath();
        String theGETRoute = "cool-path";
        String requestPath = apiPrefix + "/" + theGETRoute;
        String body = "associated body";
        int responseCode = 200;

        Router router = new Router();
        router.setControllerResolver(this::getController);

        Properties properties = new Properties();
        properties.setProperty(theGETRoute, "IrrelevantInThisTestClass");
        router.setTheGETRoutes(properties);

        ReqContextDTO ctx = new ReqContextDTO();
        ctx.setRequestPath(requestPath);
        RouterResponse result = router.get(ctx);

        assertEquals(body, result.getBody());
        assertEquals(responseCode, result.getCode());
        assertEquals(JSON, result.getType());

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
            public void initialize(ReqContextDTO controllerData) {

            }
        };
    }

}