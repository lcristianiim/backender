package org.interactor.modules.logging;

import org.interactor.modules.ModuleImplementationLoader;

public enum LoggerService {
    INSTANCE;
    private final ApplicationLogging logger;

    LoggerService() {
        this.logger = ModuleImplementationLoader.load(ApplicationLogging.class);
    }

    public ApplicationLogging getLogging() {
        return logger;
    }
}
