package org.backender.jwtauth;

import org.interactor.modules.jwtauth.JWTTokens;

public record LoginUserResponse(
        boolean isSuccess,
        JWTAuthServerErrorResponse errorResponse,
        JWTTokens tokens
) {
}
