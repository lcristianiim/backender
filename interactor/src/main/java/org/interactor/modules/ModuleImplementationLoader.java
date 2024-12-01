package org.interactor.modules;

import java.util.Optional;
import java.util.ServiceLoader;

public class ModuleImplementationLoader {
    public static <T> T load(Class<T> clazz) {

        ServiceLoader<T> sl
                = ServiceLoader.load(clazz);

        Optional<T> implementation = sl.findFirst();

        if (implementation.isEmpty())
            throw new ModuleImplementationLoader.NoModuleImplementationFoundException();

        return implementation.get();
    }


    private static class NoModuleImplementationFoundException extends RuntimeException {
        public NoModuleImplementationFoundException() {
            super();
        }
    }
}
