package org.interactor.router;

@FunctionalInterface
public interface ControllerResolver<T, U, V> {
    V apply(T clazz, U controllerData);
}
