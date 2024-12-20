package org.interactor;

import org.interactor.modules.router.dtos.InteractorResponse;
import org.interactor.modules.router.dtos.InteractorRequest;
import org.interactor.modules.router.dtos.RequestType;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InteractorEntryTest {

    @Test
    @Disabled
    void givenRegisteredRoute_ShouldProperlySolveItViaTheInteractorEntry() {
        InteractorEntry interactorEntry = new InteractorEntry();
        InteractorRequest ctx = new InteractorRequest();
        ctx.setRequestPath(ApplicationConfiguration.INSTANCE.getApiPath() + "/test-users");
        ctx.setRequestType(RequestType.GET);

        InteractorResponse result = interactorEntry.processRequest(ctx);

        assertEquals("me", result.getBody());

    }
}