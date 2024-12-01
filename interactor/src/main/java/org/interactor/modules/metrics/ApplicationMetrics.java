package org.interactor.modules.metrics;

public interface ApplicationMetrics {
    String getMetrics();
    void incrementCounter(String counter);
}
