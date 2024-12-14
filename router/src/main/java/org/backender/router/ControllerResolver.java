package org.backender.router;

@FunctionalInterface
public interface ControllerResolver<T, U, V> {
    V apply(T clazz, U controllerData);
}
