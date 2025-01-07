package org.backender.jwtauth;

import org.interactor.modules.jwtauth.*;
import org.interactor.modules.router.dtos.Principal;

import java.util.List;

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
        List<String> roles = jwtPrincipal.roles().stream()
                .map(JWTRole::name)
                .toList();

        return new Principal.Builder()
                .setName(jwtPrincipal.name())
                .setUuid(jwtPrincipal.identifier())
                .setRoles(roles)
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
        Requester requester = new Requester();
        RefreshTokenResponse response = requester.refreshToken(refreshTokenInput);

        if (response.isSuccess()) {
            JWTActionResponse jwtActionResponse = new JWTActionResponse(true, "success");
            return new JWTActionResponseWithTokens(jwtActionResponse, response.tokens());
        } else {
            JWTActionResponse jwtActionResponse = new JWTActionResponse(false, response.errorResponse().message());
            return new JWTActionResponseWithTokens(jwtActionResponse, null);
        }
    }

    @Override
    public JWTActionResponse register(InputForUserRegistration inputForUserRegistration) {
        Requester requester = new Requester();
        RegisterUserResponse response = requester.register(inputForUserRegistration);

        if (response.isSuccess()) {
            return new JWTActionResponse(true, "User successfully registered");
        } else {
            return new JWTActionResponse(false, response.errorResponse().message());
        }
    }

    @Override
    public JWTActionResponse confirm(String confirmCode) {
        Requester requester = new Requester();
        ConfirmUserResponse response = requester.confirm(confirmCode);

        if (response.isSuccess()) {
            return new JWTActionResponse(true, "User successfully confirm");
        } else {
            return new JWTActionResponse(false, response.errorResponse().message());
        }
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
