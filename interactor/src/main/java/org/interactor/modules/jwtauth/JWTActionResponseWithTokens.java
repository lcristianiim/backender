package org.interactor.modules.jwtauth;

public record JWTActionResponseWithTokens(
        JWTActionResponse jwtActionResponse,
        JWTTokens JWTTokens
) {
}
