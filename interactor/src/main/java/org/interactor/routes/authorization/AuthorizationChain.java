package org.interactor.routes.authorization;

import org.interactor.modules.router.dtos.InteractorRequest;
import org.interactor.modules.router.dtos.Principal;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AuthorizationChain {

    private final List<AuthorizationMechanismFilter> filters = new ArrayList<>();

    public void addFilter(AuthorizationMechanismFilter filter) {
        filters.add(filter);
    }

    public Optional<Principal> execute(InteractorRequest ctx) {
        List<Optional<Principal>> result = new ArrayList<>();
        for (AuthorizationMechanismFilter filter : filters) {
            result.add(filter.execute(ctx));
        }
        return result.getLast();
    }
}
