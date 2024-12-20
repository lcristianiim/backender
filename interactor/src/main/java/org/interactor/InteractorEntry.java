package org.interactor;

import org.interactor.internals.AuthenticationFilter;
import org.interactor.internals.AuthorizationFilter;
import org.interactor.internals.EntryChain;
import org.interactor.internals.RouterFilter;
import org.interactor.modules.router.dtos.InteractorResponse;
import org.interactor.modules.router.dtos.InteractorRequest;

public class InteractorEntry {

    public InteractorResponse processRequest(InteractorRequest ctx) {

        AuthenticationFilter authenticationFilter = new AuthenticationFilter();
        AuthorizationFilter authorizationFilter = new AuthorizationFilter();
        RouterFilter routerFilter = new RouterFilter();

        authenticationFilter.setNextHandler(authorizationFilter);
        authorizationFilter.setNextHandler(routerFilter);

        EntryChain chain = new EntryChain();
        chain.setFirstHandler(authenticationFilter);

        return chain.handleRequest(ctx);
    }

}
