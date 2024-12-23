package org.interactor.modules.jwtauth;

public record TokensResponse(
        String accessToken,
        String refreshToken
) {}
