package org.interactor.routes.chain;

import org.interactor.configuration.Route;
import org.interactor.modules.router.RouterService;
import org.interactor.modules.router.dtos.InteractorRequest;
import org.interactor.modules.router.dtos.InteractorResponse;

import java.util.Optional;

public class RouterFilter implements RequestFilter {
    RequestFilter nextFilter;

    @Override
    public void setNextHandler(RequestFilter filter) {
        nextFilter = filter;
    }

    @Override
    public InteractorResponse execute(InteractorRequest ctx) {
        Optional<Route> route = RouterService.INSTANCE.getRouter()
                .getRegisteredRoute(ctx.getRequestPath(), ctx.getRequestType());

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
        return response;
    }

    public InteractorResponse invalidRequestResponse(String pathWithoutAPI) {
        InteractorResponse response = new InteractorResponse();
        response.setBody("Path: " + pathWithoutAPI + " is not part of the API");
        response.setCode(404);
        return response;
    }
}
