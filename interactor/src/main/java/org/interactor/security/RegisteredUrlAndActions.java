package org.interactor.security;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class RegisteredUrlAndActions {

    private static List<UrlSecurityConfiguration> getAllUrlMappings() {
        return List.of(
                new UrlSecurityConfiguration("/", Action.ACCESS_HOMEPAGE, Collections.emptyList())
        );
    }

    public static Optional<UrlSecurityConfiguration> getConfiguration(String url) {
        return getAllUrlMappings().stream()
                .filter(e -> matchUrls(e.getUrl(), url))
                .findFirst();
    };

    static boolean matchUrls(String inputUrl, String urlToMatchWith) {
        return inputUrl.equals(urlToMatchWith);
    }
}
