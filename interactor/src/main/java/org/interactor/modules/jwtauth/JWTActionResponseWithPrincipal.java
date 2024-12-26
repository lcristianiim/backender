package org.interactor.modules.jwtauth;

import org.interactor.modules.router.dtos.Principal;

public record JWTActionResponseWithPrincipal(
        JWTActionResponse jwtActionResponse,
        Principal principal
) {
}
