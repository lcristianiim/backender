package org.interactor.internals;

import org.interactor.configuration.RegisteredRoute;
import org.interactor.modules.router.RouterService;
import org.interactor.modules.router.dtos.InteractorRequest;
import org.interactor.modules.router.dtos.InteractorResponse;
import org.interactor.modules.router.dtos.ResponseType;

import java.util.Optional;

public class RouterFilter implements RequestFilter {
    RequestFilter nextFilter;

    @Override
    public void setNextHandler(RequestFilter filter) {
        nextFilter = filter;
    }

    @Override
    public InteractorResponse execute(InteractorRequest ctx) {
        Optional<RegisteredRoute> route = RouterService.INSTANCE.getRouter().getRegisteredRoute(ctx);

        if (route.isEmpty()) {
            if (nextFilter != null) {
                nextFilter.execute(ctx);
            } else {
                return invalidRequestResponse(ctx.getRequestPath());
            }
        }

        return RouterService.INSTANCE.getRouter().processRequest(ctx);
    }

    private InteractorResponse requestNotHandled() {
        InteractorResponse response = new InteractorResponse();
        response.setCode(503);
        response.setBody("The request was not handled by the Authentication Filter");
        response.setType(ResponseType.JSON);
        return response;
    }

    public InteractorResponse invalidRequestResponse(String pathWithoutAPI) {
        InteractorResponse response = new InteractorResponse();
        response.setBody("Path: " + pathWithoutAPI + " is not part of the API");
        response.setCode(500);
        response.setType(ResponseType.JSON);
        return response;
    }
}
