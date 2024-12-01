package org.interactor.modules.metrics;

import org.interactor.modules.ModuleImplementationLoader;

public enum MetricsService {
    INSTANCE;
    private final ApplicationMetrics metrics;

    MetricsService() {
        metrics = ModuleImplementationLoader.load(ApplicationMetrics.class);
    }

    public void incrementCounter() {
        INSTANCE.metrics.incrementCounter("instance");
    }

    public String getMetrics() {
        return INSTANCE.metrics.getMetrics();
    }

}
