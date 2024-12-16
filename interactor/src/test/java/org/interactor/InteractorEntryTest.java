package org.interactor;

import org.interactor.modules.router.dtos.InteractorResponse;
import org.interactor.modules.router.dtos.ReqContextDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InteractorEntryTest {

    @Test
    void a() {
        InteractorEntry interactorEntry = new InteractorEntry();
        ReqContextDTO ctx = new ReqContextDTO();
        ctx.setRequestPath(ApplicationConfiguration.INSTANCE.getApiPath() + "/test-users");

        InteractorResponse result = interactorEntry.processGETRequest(ctx);

        assertEquals("me", result.getBody());

    }
}