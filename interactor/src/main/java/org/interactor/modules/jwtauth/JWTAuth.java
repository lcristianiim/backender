package org.interactor.modules.jwtauth;

import org.interactor.modules.router.dtos.Principal;

import java.time.LocalDateTime;

public interface JWTAuth {
    Principal decode(String token);
    TokensResponse login(LoginInput loginInput);
    TokensResponse refreshToken(RefreshTokenInput refreshTokenInput);
    boolean register(RegisterInput registerInput);
    boolean confirm(String confirmCode);

    boolean suspend(String userUUID, LocalDateTime start, LocalDateTime end);
    boolean unSuspend(String userUUID);
    boolean cancel(String userUUID);
    boolean unCancel(String userUUID);
}
