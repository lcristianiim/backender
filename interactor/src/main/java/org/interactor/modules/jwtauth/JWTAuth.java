package org.interactor.modules.jwtauth;

public interface JWTAuth {
    JWTActionResponseWithPrincipal decode(String token);
    JWTActionResponseWithTokens login(LoginInput loginInput);
    JWTActionResponseWithTokens refreshToken(RefreshTokenInput refreshTokenInput);
    JWTActionResponse register(InputForUserRegistration inputForUserRegistration);
    JWTActionResponse confirm(String confirmCode);

    JWTActionResponse suspend(InputForUserSuspend suspendInput);
    JWTActionResponse unSuspend(String userUUID);
    JWTActionResponse cancel(String userUUID);
    JWTActionResponse unCancel(String userUUID);
}