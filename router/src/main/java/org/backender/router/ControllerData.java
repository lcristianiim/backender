package org.backender.router;

import java.util.List;
import java.util.Locale;

public record ControllerData(String requestPath,
                             Locale locale,
                             List<String> pathParameters,
                             List<String> queryParameters) {
}
