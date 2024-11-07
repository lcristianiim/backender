package org.backender.logger;

import org.interactor.router.Router;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class LoggerImplementationTest {

    @Test
    @Disabled("This is an integration test disabled by default")
    void whenLoggingIsCalled_ShouldLog() {
        LoggerImplementation loggerImplementation = new LoggerImplementation();

        loggerImplementation.info("hello", Router.class);
    }

}