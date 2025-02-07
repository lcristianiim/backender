package org.backender.jwtauth;

public record PurgeUserResponse(
    boolean isSuccess,
    JWTAuthServerErrorResponse errorResponse
){}
