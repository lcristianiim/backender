package org.interactor.router;

import java.util.List;
import java.util.Locale;

public record ControllerData(String requestPath,
                             Locale locale,
                             String body,
                             List<String> pathParameters,
                             List<String> queryParameters) {
}
