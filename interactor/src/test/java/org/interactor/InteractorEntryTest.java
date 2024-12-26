package org.interactor;

import org.interactor.configuration.Route;
import org.interactor.modules.router.dtos.InteractorResponse;
import org.interactor.modules.router.dtos.InteractorRequest;
import org.interactor.modules.router.dtos.RequestType;
import org.interactor.modules.router.dtos.ResponseType;
import org.interactor.routes.InteractorEntry;
import org.interactor.routes.authorization.TestController;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.interactor.configuration.RegisteredRoute.setCustomRoutesForTests;
import static org.interactor.modules.router.dtos.RequestType.GET;
import static org.junit.jupiter.api.Assertions.assertEquals;

class InteractorEntryTest {

    @Test
    void givenRegisteredRoute_ShouldProperlySolveItViaTheInteractorEntry() {

        setCustomRoutesForTests(List.of(new Route(
                GET, "test-users", new TestController(), List.of()
        )));

        InteractorEntry interactorEntry = new InteractorEntry();
        InteractorRequest ctx = new InteractorRequest();
        ctx.setRequestPath("test-users");
        ctx.setRequestType(RequestType.GET);

        InteractorResponse result = interactorEntry.processRequest(ctx);


        assertEquals(111, result.getCode());
        assertEquals(ResponseType.JSON, result.getType());

    }
}