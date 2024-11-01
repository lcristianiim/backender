package org.backender.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.prometheusmetrics.PrometheusConfig;
import io.micrometer.prometheusmetrics.PrometheusMeterRegistry;
import org.interactor.modules.metrics.ApplicationMetrics;

public class PrometheusMetrics implements ApplicationMetrics {
    private static final PrometheusMeterRegistry prometheusRegistry = createPrometheusMetrics();

    public PrometheusMetrics() {
        // should be private but is needed for JPMS
        // never use this constructor
    }

    public String getMetrics() {
        return prometheusRegistry.scrape();
    }

    @Override
    public void incrementCounter(String counter) {
        prometheusRegistry.get(counter).counter().increment();
    }

    private static PrometheusMeterRegistry createPrometheusMetrics() {
        PrometheusMeterRegistry prometheusRegistry = new PrometheusMeterRegistry(PrometheusConfig.DEFAULT);

        Counter
                .builder("instance")
                .description("indicates instance count of the object")
                .tags("dev", "performance")
                .register(prometheusRegistry);

        Metrics.addRegistry(prometheusRegistry);

        return prometheusRegistry;
    }

}