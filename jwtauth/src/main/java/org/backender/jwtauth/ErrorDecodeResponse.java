package org.backender.jwtauth;

public record ErrorDecodeResponse(
        String statusCode,
        String code,
        String error,
        String message
) {
}
