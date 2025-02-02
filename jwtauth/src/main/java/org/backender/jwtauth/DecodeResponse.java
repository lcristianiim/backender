package org.backender.jwtauth;

import java.util.Optional;

public record DecodeResponse(int statusCode, String message, Optional<JWTPrincipal> principal) {
}
