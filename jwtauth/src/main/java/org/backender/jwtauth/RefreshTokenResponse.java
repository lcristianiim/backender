package org.backender.jwtauth;

import org.interactor.modules.jwtauth.JWTTokens;

public record RefreshTokenResponse(
        boolean isSuccess,
        JWTAuthServerErrorResponse errorResponse,
        JWTTokens tokens
) {
}
