package org.backender.jwtauth;

public record JWTAuthServerErrorResponse(
        String statusCode,
        String code,
        String error,
        String message
) {
}
