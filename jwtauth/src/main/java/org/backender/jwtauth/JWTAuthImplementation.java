package org.backender.jwtauth;

import org.interactor.modules.jwtauth.*;
import org.interactor.modules.router.dtos.Principal;

import java.util.List;

public class JWTAuthImplementation implements JWTAuth {
    @Override
    public JWTActionResponseWithPrincipal decode(String token) {
        JWTAuthRequester requester = new JWTAuthRequester();
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
        JWTAuthRequester requester = new JWTAuthRequester();
        LoginUserResponse response = requester.login(loginInput);

        if (response.isSuccess()) {
            JWTActionResponse jwtActionResponse = new JWTActionResponse(true, "success");
            return new JWTActionResponseWithTokens(jwtActionResponse, response.tokens());
        } else {
            JWTActionResponse jwtActionResponse = new JWTActionResponse(false, response.errorResponse().body());
            return new JWTActionResponseWithTokens(jwtActionResponse, null);
        }
    }

    @Override
    public JWTActionResponseWithTokens refreshToken(RefreshTokenInput refreshTokenInput) {
        JWTAuthRequester requester = new JWTAuthRequester();
        RefreshTokenResponse response = requester.refreshToken(refreshTokenInput);

        if (response.isSuccess()) {
            JWTActionResponse jwtActionResponse = new JWTActionResponse(true, "success");
            return new JWTActionResponseWithTokens(jwtActionResponse, response.tokens());
        } else {
            JWTActionResponse jwtActionResponse = new JWTActionResponse(false, response.errorResponse().body());
            return new JWTActionResponseWithTokens(jwtActionResponse, null);
        }
    }

    @Override
    public JWTActionResponse register(InputForUserRegistration inputForUserRegistration) {
        JWTAuthRequester requester = new JWTAuthRequester();
        RegisterUserResponse response = requester.register(inputForUserRegistration);

        if (response.isSuccess()) {
            return new JWTActionResponse(true, "User successfully registered");
        } else {
            return new JWTActionResponse(false, response.errorResponse().body());
        }
    }

    @Override
    public JWTActionResponse confirm(String confirmCode) {
        JWTAuthRequester requester = new JWTAuthRequester();
        ConfirmUserResponse response = requester.confirm(confirmCode);

        if (response.isSuccess()) {
            return new JWTActionResponse(true, "User successfully confirm");
        } else {
            return new JWTActionResponse(false, response.errorResponse().body());
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
