package org.backender.jwtauth;

public record ConfirmUserResponse(
    boolean isSuccess,
    JWTAuthServerErrorResponse errorResponse
){}
