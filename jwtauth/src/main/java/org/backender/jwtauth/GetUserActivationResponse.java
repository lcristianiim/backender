package org.backender.jwtauth;

public record GetUserActivationResponse(
    boolean isSuccess,
    String confirmationLink,
    String uuid,
    String userIdentifier,
    JWTAuthServerErrorResponse errorResponse
){}
