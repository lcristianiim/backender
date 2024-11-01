package org.interactor.request;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RequestHandlerTest {

    @Test
    void b() {
        RequestHandler handler = new RequestHandler();
        Page page = handler.processRequest("unregistered-url", null, null);

        assertEquals("page-not-found", page.getView());
        assertEquals("Page not found", page.getModel().get("message"));
        assertEquals(404, page.getResponseCode());
    }

    @Test
    @Disabled
    void a() {
        RequestHandler handler = new RequestHandler();
        Page page = handler.processRequest("/", null, null);

        assertEquals("/", page.getView());
        assertEquals("hello world", page.getModel().get("message"));
    }

}