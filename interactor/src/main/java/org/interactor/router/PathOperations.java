package org.interactor.router;

import org.interactor.ApplicationConfigurationSingleton;

import java.util.function.Function;

public class PathOperations {

    public static Function<String,String> getPathWithoutTheAPIPart() {
        String apiPath = ApplicationConfigurationSingleton.INSTANCE.getApiPath();
        return (s) -> s.split(apiPath + "/")[1];
    }
}
