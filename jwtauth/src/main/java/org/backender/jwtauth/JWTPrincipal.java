package org.backender.jwtauth;

import org.interactor.modules.jwtauth.JWTRole;

import java.util.List;

public record JWTPrincipal (
    String exp,
    String iat,
    String aud,
    String sub,
    String iss,
    List<JWTRole> roles,
    String identifier,
    String name
) {}
