package org.interactor;

import org.interactor.configuration.RegisteredRoute;
import org.interactor.modules.router.RouterService;
import org.interactor.modules.router.dtos.InteractorResponse;
import org.interactor.modules.router.dtos.ReqContextDTO;
import org.interactor.modules.router.dtos.ResponseType;

import java.util.Optional;

public class InteractorEntry {
    InteractorResponse processGETRequest(ReqContextDTO ctx) {
        InteractorResponse response = new InteractorResponse();
        Optional<RegisteredRoute> route = RouterService.INSTANCE.getRouter().getRegisteredRoute(ctx);

        if (route.isEmpty())
            return invalidRequestResponse(ctx.getRequestPath());



        return response;
    }

    private static InteractorResponse invalidRequestResponse(String pathWithoutAPI) {
        InteractorResponse response = new InteractorResponse();
        response.setBody("Path: " + pathWithoutAPI + " is not part of the API");
        response.setCode(500);
        response.setType(ResponseType.JSON);
        return response;
    }
}
