package org.backender.jwtauth;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RefreshTokenInputDTO(
        @JsonProperty("grant_type")
        String grandType,
        @JsonProperty("refresh_token")
        String refreshToken
) {}

