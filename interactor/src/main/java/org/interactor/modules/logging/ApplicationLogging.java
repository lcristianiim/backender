package org.interactor.modules.logging;

public interface ApplicationLogging {
    void trace(String message, Class<?> clazz);
    void debug(String message, Class<?> clazz);
    void info(String message, Class<?> clazz);
    void warn(String message, Class<?> clazz);
    void error(String message, Class<?> clazz);
}