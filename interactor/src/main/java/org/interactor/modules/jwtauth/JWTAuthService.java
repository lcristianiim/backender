package org.interactor.modules.jwtauth;

import org.interactor.modules.ModuleImplementationLoader;

public enum JWTAuthService {
    INSTANCE;
    private final JWTAuth jwtAuth;

    JWTAuthService() {
        jwtAuth = ModuleImplementationLoader.load(JWTAuth.class);
    }

    public JWTAuth getJwtAuth() {
        return jwtAuth;
    }
}
