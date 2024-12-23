package org.interactor.routes.authorization;

import org.interactor.modules.router.dtos.InteractorRequest;
import org.interactor.modules.router.dtos.Principal;
import org.interactor.routes.chain.RequestFilter;

import java.util.Optional;

public interface AuthorizationMechanismFilter {
    void setNextHandler(AuthorizationMechanismFilter nextHandler);
    Optional<Principal> execute(InteractorRequest ctx);
}
