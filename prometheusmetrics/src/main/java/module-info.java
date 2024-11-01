import org.interactor.modules.metrics.ApplicationMetrics;

module org.prometheusmetrics {
    requires org.interactormodule;
    requires micrometer.registry.prometheus;
    requires micrometer.core;

    exports org.backender.metrics;

    provides ApplicationMetrics with org.backender.metrics.PrometheusMetrics;
}