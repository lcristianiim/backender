package org.interactor.modules.jwtauth;

import java.time.LocalDateTime;

public interface JWTAuth {
    JWTActionResponseWithPrincipal decode(String token);
    JWTActionResponseWithTokens login(LoginInput loginInput);
    JWTActionResponseWithTokens refreshToken(RefreshTokenInput refreshTokenInput);
    JWTActionResponse register(InputForUserRegistration inputForUserRegistration);
    JWTActionResponse confirm(String confirmCode);

    JWTActionResponse suspend(String userUUID, LocalDateTime start, LocalDateTime end);
    JWTActionResponse unSuspend(String userUUID);
    JWTActionResponse cancel(String userUUID);
    JWTActionResponse unCancel(String userUUID);
}
