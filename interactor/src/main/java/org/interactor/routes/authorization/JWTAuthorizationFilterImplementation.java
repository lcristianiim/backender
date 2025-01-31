package org.interactor.routes.authorization;

import org.interactor.modules.jwtauth.JWTActionResponseWithPrincipal;
import org.interactor.modules.jwtauth.JWTAuth;
import org.interactor.modules.router.dtos.InteractorRequest;
import org.interactor.modules.router.dtos.Principal;

import java.util.Optional;

public class JWTAuthorizationFilterImplementation implements AuthorizationMechanismFilter {
    private JWTAuth jwtAuth;
    private AuthorizationMechanismFilter nextHandler;

    @Override
    public void setNextHandler(AuthorizationMechanismFilter nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public Optional<Principal> execute(InteractorRequest ctx) {
        if (isValidAuthorizationSetOnRequest(ctx.getAuthorization())) {
            JWTActionResponseWithPrincipal response = jwtAuth.decode(ctx.getAuthorization().split(" ")[1]);
            if (response.jwtActionResponse().isSuccess()) {
                return Optional.of(response.principal());
            }
        } else if (null != nextHandler) {
            return nextHandler.execute(ctx);
        }

        return Optional.empty();
    }

    private boolean isValidAuthorizationSetOnRequest(String authorization) {
        if (authorization == null)
            return false;
        String[] authorizationSplit = authorization.split(" ");
        int authorizationSplitLength = authorizationSplit.length;
        return authorizationSplitLength == 2
                && authorizationSplit[0].equals("Bearer")
                && !authorizationSplit[1].isEmpty();
    }

    public void setJwtAuth(JWTAuth jwtAuth) {
        this.jwtAuth = jwtAuth;
    }
}
