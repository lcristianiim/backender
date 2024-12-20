package org.interactor;

import org.interactor.modules.router.dtos.InteractorResponse;
import org.interactor.modules.router.dtos.InteractorRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InteractorEntryTest {

    @Test
    void a() {
        InteractorEntry interactorEntry = new InteractorEntry();
        InteractorRequest ctx = new InteractorRequest();
        ctx.setRequestPath(ApplicationConfiguration.INSTANCE.getApiPath() + "/test-users");

        InteractorResponse result = interactorEntry.processRequest(ctx);

        assertEquals("me", result.getBody());

    }
}