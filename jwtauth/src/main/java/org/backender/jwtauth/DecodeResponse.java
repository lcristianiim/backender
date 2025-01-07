package org.backender.jwtauth;

import java.util.Optional;

public record DecodeResponse(String statusCode, String message, Optional<JWTPrincipal> principal) {
}
