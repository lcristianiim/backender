package org.backender.jwtauth;

import org.interactor.modules.jwtauth.*;
import org.interactor.modules.router.dtos.Principal;

public class JWTAuthImplementation implements JWTAuth {
    @Override
    public JWTActionResponseWithPrincipal decode(String token) {
        Requester requester = new Requester();
        DecodeResponse jwtDecodeResponse = requester.decode(token);

        if (jwtDecodeResponse.principal().isPresent()) {
            JWTActionResponse jwtActionResponse = new JWTActionResponse(true, jwtDecodeResponse.message());
            Principal interactorPrincipal = convertJWTPrincipal(jwtDecodeResponse.principal().get());
            return new JWTActionResponseWithPrincipal(jwtActionResponse, interactorPrincipal);
        } else {
            JWTActionResponse jwtActionResponse = new JWTActionResponse(false, jwtDecodeResponse.message());
            return new JWTActionResponseWithPrincipal(jwtActionResponse, null);
        }
    }

    private Principal convertJWTPrincipal(JWTPrincipal jwtPrincipal) {
        return new Principal.Builder()
                .setName(jwtPrincipal.name())
                .setIdentifier(jwtPrincipal.identifier())
                .build();
    }

    @Override
    public JWTActionResponseWithTokens login(LoginInput loginInput) {
        Requester requester = new Requester();
        JWTTokens tokens = requester.login(loginInput);

        JWTActionResponse jwtActionResponse = new JWTActionResponse(true, "success");
        return new JWTActionResponseWithTokens(jwtActionResponse, tokens);
    }


    @Override
    public JWTActionResponseWithTokens refreshToken(RefreshTokenInput refreshTokenInput) {
        return null;
    }

    @Override
    public JWTActionResponse register(InputForUserRegistration inputForUserRegistration) {
        return null;
    }

    @Override
    public JWTActionResponse confirm(String confirmCode) {
        return null;
    }

    @Override
    public JWTActionResponse suspend(InputForUserSuspend suspendInput) {
        return null;
    }

    @Override
    public JWTActionResponse unSuspend(String userUUID) {
        return null;
    }

    @Override
    public JWTActionResponse cancel(String userUUID) {
        return null;
    }

    @Override
    public JWTActionResponse unCancel(String userUUID) {
        return null;
    }
}
