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

        RequestContext ctx = new RequestContext();
        ctx.setRequestPath(requestPath);
        ResponseBody result = router.get(ctx);

        assertEquals(body, result.getBody());
        assertEquals(responseCode, result.getCode());
        assertEquals(JSON, result.getType());

    }

    Controller getController(String className, RequestContext ctx) {
        return new Controller() {
            @Override
            public ResponseBody getResponse() {
                ResponseBody responseBody = new ResponseBody();
                responseBody.setType(JSON);
                responseBody.setCode(200);
                responseBody.setBody("associated body");
                return responseBody;
            }

            @Override
            public void initialize(RequestContext controllerData) {

            }
        };
    }

}