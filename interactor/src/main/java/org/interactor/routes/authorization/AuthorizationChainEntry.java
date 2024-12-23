package org.interactor.routes.authorization;

import org.interactor.modules.router.dtos.InteractorRequest;
import org.interactor.modules.router.dtos.Principal;

import java.util.Optional;

public class AuthorizationChainEntry {

    private AuthorizationMechanismFilter firstHandler;

    public void setFirstHandler(AuthorizationMechanismFilter handler) {
        this.firstHandler = handler;
    }

    public Optional<Principal> handleRequest(InteractorRequest request) {
        if (firstHandler != null) {
            return firstHandler.execute(request);
        }
        return Optional.empty();
    }

}
