package org.interactor.modules.router;

import org.interactor.modules.ModuleImplementationLoader;

public enum RouterService {
    INSTANCE;
    private final Router router;

    RouterService() {
        router = ModuleImplementationLoader.load(Router.class);
    }

    public Router getRouter() {
        return router;
    }
}
