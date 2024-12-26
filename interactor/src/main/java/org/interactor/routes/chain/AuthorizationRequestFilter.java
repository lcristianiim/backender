package org.interactor.routes.chain;

import org.interactor.modules.router.dtos.InteractorRequest;
import org.interactor.modules.router.dtos.InteractorResponse;
import org.interactor.modules.router.dtos.Principal;
import org.interactor.modules.router.dtos.ResponseType;
import org.interactor.routes.authorization.AuthorizationChainEntry;
import org.interactor.routes.authorization.AuthorizationMechanismFilter;
import org.interactor.routes.authorization.JWTAuthorizationFilterImplementation;

import java.util.Optional;

public class AuthorizationRequestFilter implements RequestFilter {
    private RequestFilter nextFilter;

    @Override
    public void setNextHandler(RequestFilter nextFilter) {
        this.nextFilter = nextFilter;
    }

    @Override
    public InteractorResponse execute(InteractorRequest ctx) {

        AuthorizationMechanismFilter jwtAuthorization = new JWTAuthorizationFilterImplementation();
        AuthorizationChainEntry chain = new AuthorizationChainEntry();
        chain.setFirstHandler(jwtAuthorization);
        Optional<Principal> principal = chain.handleRequest(ctx);

        return nextFilter.execute(ctx);
        // do checks and return bad response if something goes wrong
//        if (ctx.getAuthorization() == null) {
//            return createInvalidResponse();

            // all ok so pass to next
//        } else if (nextFilter != null) {
//            nextFilter.execute(ctx);
//        }

//        return requestNotHandled();

    }

    private InteractorResponse requestNotHandled() {
        InteractorResponse response = new InteractorResponse();
        response.setCode(503);
        response.setBody("The request was not handled by the Authorization Filter");
        return response;
    }

    private InteractorResponse createInvalidResponse() {
        InteractorResponse response = new InteractorResponse();
        response.setCode(503);
        response.setBody("Authorization failed");
        return response;
    }
}
