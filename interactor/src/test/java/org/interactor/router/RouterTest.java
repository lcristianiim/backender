package org.interactor.router;

import org.junit.jupiter.api.Test;

import java.util.Locale;

class RouterTest {

    @Test
    void a() {
        Router router = new Router();
        Locale locale = Locale.of("ro");

        router.get("/home", locale);
    }
}