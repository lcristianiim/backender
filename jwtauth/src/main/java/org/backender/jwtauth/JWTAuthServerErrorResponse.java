package org.backender.jwtauth;

public record JWTAuthServerErrorResponse(
        int statusCode,
        String statusText,
        String body
) {
}
