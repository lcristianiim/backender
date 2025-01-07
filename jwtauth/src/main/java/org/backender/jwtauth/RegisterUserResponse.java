package org.backender.jwtauth;

public record RegisterUserResponse(
    boolean isSuccess,
    JWTAuthServerErrorResponse errorResponse
){}
