package org.interactor.routes;

import org.interactor.modules.router.dtos.InteractorResponse;
import org.interactor.modules.router.dtos.InteractorRequest;
import org.interactor.routes.chain.*;

public class InteractorEntry {

    public InteractorResponse processRequest(InteractorRequest ctx) {

        RequestFilter authenticationFilter = new AuthenticationFilter();
        RequestFilter authorizationFilter = new AuthorizationRequestFilter();
        RequestFilter routerFilter = new RouterFilter();

        ChainEntry chain = new ChainEntry();

        authenticationFilter.setNextHandler(authorizationFilter);
        authorizationFilter.setNextHandler(routerFilter);

        chain.setFirstHandler(authenticationFilter);
        return chain.handleRequest(ctx);
    }

}
