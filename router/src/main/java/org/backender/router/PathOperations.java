package org.backender.router;

import org.interactor.configuration.Configuration;

public class PathOperations {

    public static String getPathWithoutTheAPIPart(String pathWithApi) {
        String apiPath = Configuration.API_PATH.getValue();
        if (null == pathWithApi)
            return "";

        String[] split = pathWithApi.split(apiPath + "/");
        if (split.length > 0) {
            return split[1];
        } else {
            return split[0];
        }
    };
}
