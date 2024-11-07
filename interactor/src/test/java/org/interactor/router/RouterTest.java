package org.interactor.router;

import org.interactor.ApplicationConfigurationSingleton;
import org.junit.jupiter.api.Test;

import java.util.Locale;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;
class RouterTest {

    @Test
    public void givenPathRegisteredInProperties_ShouldReturnTheAssociatedRequestBody() {
        String apiPrefix = ApplicationConfigurationSingleton.INSTANCE.getApiPath();
        String theGETRoute = "cool-path";
        String requestPath = apiPrefix + "/" + theGETRoute;
        String body = "associated body";
        int responseCode = 200;

        Router router = new Router();
        router.setControllerResolver((s) -> () -> new ResponseBody(body, responseCode));

        Properties properties = new Properties();
        properties.setProperty(theGETRoute, "IrrelevantInThisTestClass");
        router.setTheGETRoutes(properties);

        ResponseBody result = router.get(requestPath, Locale.of("irrelevant"));

        assertEquals(body, result.body());
        assertEquals(responseCode, result.code());
    }

}