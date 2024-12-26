package org.interactor.modules.jwtauth;

public record JWTActionResponse (
    boolean isSuccess,
    String message
) {}
