package org.interactor.router;

import org.junit.jupiter.api.Test;

import java.util.Locale;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;
class RouterTest {


    @Test
    public void givenPathRegisteredInProperties_ShouldReturnTheAssociatedRequestBody() {
        String path = "cool-path";
        String body = "associated body";
        int responseCode = 200;

        Router router = new Router();
        router.setGetResponseBodySideEffect((s) -> new ResponseBody(body, responseCode));
        router.setPropertiesSideEffects(() -> {
            Properties properties = new Properties();
            properties.setProperty(path, "IrrelevantInThisTestClass");

            return properties;
        });

        ResponseBody result = router.get(path, Locale.of("irrelevant"));

        assertEquals(body, result.body());
        assertEquals(responseCode, result.code());
    }
}