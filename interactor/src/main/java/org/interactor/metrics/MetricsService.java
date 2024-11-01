package org.interactor.metrics;

import java.util.Optional;
import java.util.ServiceLoader;

public class MetricsService {
    private static ApplicationMetrics metrics;

    public MetricsService() {
        if (metrics == null) {
            setMetricsProvider();
        }
    }

    public String getMetrics() {
        return metrics.getMetrics();
    }

    public void incrementCounter() {
        metrics.incrementCounter("instance");
    }

    private void setMetricsProvider() {
        ServiceLoader<ApplicationMetrics> sl
                = ServiceLoader.load(ApplicationMetrics.class);

        Optional<ApplicationMetrics> implementation = sl.findFirst();

        if (implementation.isEmpty())
            throw new NoMetricsServiceException();

        metrics = implementation.get();
    }

    private static class NoMetricsServiceException extends RuntimeException {
        public NoMetricsServiceException() {
            super();
        }
    }
}
