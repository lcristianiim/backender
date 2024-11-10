package org.backender.logger;

import org.interactor.modules.logging.ApplicationLogging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LoggerImplementation implements ApplicationLogging {

    private final List<Logger> loggerObjects = new ArrayList<>();


    public LoggerImplementation() {
        // this is needed by the jpms
    }

    @Override
    public void trace(String message, Class<?> clazz) {
        Logger loggerImplementation = getLogger(clazz);
        loggerImplementation.trace(message);
    }

    @Override
    public void debug(String message, Class<?> clazz) {
        Logger loggerImplementation = getLogger(clazz);
        loggerImplementation.debug(message);
    }

    @Override
    public void info(String message, Class<?> clazz) {
        Optional<Logger> classLogger = loggerObjects.stream()
                .filter(e -> e.getName().equals(clazz.getName()))
                .findFirst();

        classLogger.ifPresentOrElse(
                value -> Thread.ofVirtual().start(() -> {
                    value.info(message);
                }),
                () -> Thread.ofVirtual().start(() -> {
                    Logger loggerImplementation = createLoggerAndAddItToLoggerObjects(clazz);
                    loggerImplementation.info(message);
                })
        );
    }

    private Logger createLoggerAndAddItToLoggerObjects(Class<?> clazz) {
        Logger loggerImplementation = getLogger(clazz);
        loggerObjects.add(loggerImplementation);
        return loggerImplementation;
    }

    @Override
    public void warn(String message, Class<?> clazz) {
        Logger loggerImplementation = getLogger(clazz);
        loggerImplementation.warn(message);
    }

    @Override
    public void error(String message, Class<?> clazz) {
        Logger loggerImplementation = getLogger(clazz);
        loggerImplementation.error(message);
    }

    public Logger getLogger(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }
}
