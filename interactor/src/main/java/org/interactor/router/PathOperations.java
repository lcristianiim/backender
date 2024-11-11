package org.interactor.router;

import org.interactor.ApplicationConfiguration;

import java.util.function.Function;

public class PathOperations {

    public static Function<String,String> getPathWithoutTheAPIPart() {
        String apiPath = ApplicationConfiguration.INSTANCE.getApiPath();
        return (s) -> s.split(apiPath + "/")[1];
    }
}
