package org.interactor.modules.jwtauth;

public record JWTTokens(
        String accessToken,
        String refreshToken
) {}
