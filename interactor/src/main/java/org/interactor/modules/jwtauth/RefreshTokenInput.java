package org.interactor.modules.jwtauth;

public record RefreshTokenInput (
        String grandType,
        String refreshToken
) {}

