package org.interactor.routes.chain;

import org.interactor.configuration.Route;
import org.interactor.modules.router.RouterService;
import org.interactor.modules.router.dtos.InteractorRequest;
import org.interactor.modules.router.dtos.InteractorResponse;
import org.interactor.modules.router.dtos.ResponseType;

import java.util.Optional;

public class AuthenticationFilter implements RequestFilter {
    private RequestFilter nextFilter;

    @Override
    public void setNextHandler(RequestFilter nextFilter) {
        this.nextFilter = nextFilter;
    }

    @Override
    public InteractorResponse execute(InteractorRequest ctx) {
        Optional<Route> route = RouterService.INSTANCE.getRouter()
                .getRegisteredRoute(ctx.getRequestPath(), ctx.getRequestType());

        if ((route.isEmpty() || route.get().roles().isEmpty()) && nextFilter != null) {
            return nextFilter.execute(ctx);

        } else if ((route.isPresent() && !route.get().roles().isEmpty()) && (null == ctx.getAuthorization() || ctx.getAuthorization().isEmpty())) {
            return authenticationRequiredResponse();

        } else if (nextFilter != null) {
            return nextFilter.execute(ctx);
        }

        return createInvalidResponse();

    }

    private InteractorResponse createInvalidResponse() {
        InteractorResponse response = new InteractorResponse();
        response.setCode(503);
        response.setBody("Authentication failed");
        response.setType(ResponseType.JSON);
        return response;
    }

    private InteractorResponse authenticationRequiredResponse() {
        InteractorResponse response = new InteractorResponse();
        response.setCode(403);
        response.setBody("You need to be authenticated to access this request");
        response.setType(ResponseType.JSON);
        return response;
    }
}
