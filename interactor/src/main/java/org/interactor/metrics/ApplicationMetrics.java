package org.interactor.metrics;

public interface ApplicationMetrics {
    String getMetrics();
    void incrementCounter(String counter);
}
