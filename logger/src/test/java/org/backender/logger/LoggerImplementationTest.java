package org.backender.logger;

import org.interactor.modules.router.dtos.Controller;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class LoggerImplementationTest {

    @Test
    @Disabled("This is an integration test disabled by default")
    void whenLoggingIsCalled_ShouldLog() {
        LoggerImplementation loggerImplementation = new LoggerImplementation();

        loggerImplementation.info("hello", Controller.class);
    }

}