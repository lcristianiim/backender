package org.backender.router;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PathCommonOperations {

    public Map<String, String> getPathParams(String registeredPath, String path) {
        Map<String, String> result = new HashMap<>();
        String[] splitPath = path.split("/");
        String[] splitRegisteredPath = registeredPath.split("/");

        for (int i = 0; i < splitRegisteredPath.length; i++) {
            if (splitRegisteredPath[i].contains("{") && splitRegisteredPath[i].contains("}")) {
                int indexOfQueryParamSymbol = splitRegisteredPath[i].indexOf("?");
                if (indexOfQueryParamSymbol == -1) {
                    String key = splitRegisteredPath[i].replace("{", "").replace("}","");
                    if (i < splitPath.length)
                        result.put(key, splitPath[i].split("\\?")[0]);
                } else {
                    String pathParamWithoutQuery = splitRegisteredPath[i].substring(0, indexOfQueryParamSymbol);
                    String key = pathParamWithoutQuery.replace("{", "").replace("}","");
                    if (i < splitPath.length)
                        result.put(key, splitPath[i].split("\\?")[0]);
                }
            }
        }

        return result;
    }

    public Map<String, String> getQueryParams(String registeredPath, String requestPath) {
        Map<String, String> result = new HashMap<>();
        String[] splitRegisteredPath = registeredPath
                .split("\\?");

        try {
            Map<String, String> extractedQueryParams = getQueryParams(
                    requestPath
                            .replace("{", "")
                            .replace("}", "")
            );

            if (splitRegisteredPath.length > 1) {
                if (splitRegisteredPath[1].contains("&")) {
                    String[] registeredValues = splitRegisteredPath[1].split("&");
                    for (String registeredValue : registeredValues) {
                        result.put(registeredValue, extractedQueryParams.get(registeredValue));
                    }

                } else {
                    for (int i = 1; i < splitRegisteredPath.length; i++) {
                        result.put(splitRegisteredPath[i], extractedQueryParams.get(splitRegisteredPath[i]));
                    }
                }
            }
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    public Map<String, String> getQueryParams(String requestUrl) throws URISyntaxException {
        Map<String, String> queryParams = new HashMap<>();

        // Create a URI object from the request URL
        URI uri = new URI(requestUrl);

        // Get the query part of the URI
        String query = uri.getQuery();

        if (query != null) {
            // Split the query string into individual key-value pairs
            String[] pairs = query.split("&");
            for (String pair : pairs) {
                // Split each pair into key and value
                String[] keyValue = pair.split("=");
                String key = keyValue[0];
                String value = keyValue.length > 1 ? keyValue[1] : ""; // Handle cases where value is missing
                queryParams.put(key, value);
            }
        }

        return queryParams;
    }

    public boolean isARegisteredControllerMatch(String registeredPath, String path) {
        Map<String, String> pathParams = getPathParams(registeredPath, path);
        Map<String, String> queryParams = getQueryParams(registeredPath, path);

        String registeredPathWithPathParamsReplaced = replacePathParams(registeredPath, pathParams);
        String registeredPathWithQueryAndPathParamsReplaced = replaceQueryParams(registeredPathWithPathParamsReplaced, queryParams);

        return registeredPathWithQueryAndPathParamsReplaced.equals(path);
    }

    private String replaceQueryParams(String registeredPath, Map<String, String> queryParams) {
        String intermediaryPath = registeredPath.replace("?", "&");

        for (String queryParam : queryParams.keySet()) {
            String whatToReplace = "&" + queryParam;
            String withWhatToReplace = "&" + queryParam + "=" + queryParams.get(queryParam);
            intermediaryPath = intermediaryPath.replace(whatToReplace, withWhatToReplace);
        }


        if (intermediaryPath.contains("?")) {
            String original = intermediaryPath.replaceFirst("&", "?");
            String whatNeedsToBeReplaced = registeredPath.split("\\?")[1];
            String valueToReplace = original.split("\\?")[1];
            return registeredPath.replace(whatNeedsToBeReplaced, valueToReplace);
        }

        return intermediaryPath.replaceFirst("&", "?");
    }

    private String replacePathParams(String registeredPath, Map<String, String> pathParams) {
        List<String> templatedParams = getTemplatedParams(registeredPath);

        String matchedString = new String(registeredPath);
        for (String template : templatedParams) {
            String valueToReplaceWith = pathParams.get(template
                    .replace("{", "")
                    .replace("}", ""));
            if (valueToReplaceWith != null) {
                matchedString = matchedString.replace(template, valueToReplaceWith);
            }

        }
        return matchedString;
    }

    private List<String> getTemplatedParams(String registeredPath) {

        List<String> placeholders = new ArrayList<>();
        // Regular expression to match placeholders in the format {placeholder}
        Pattern pattern = Pattern.compile("\\{(.*?)\\}");
        Matcher matcher = pattern.matcher(registeredPath);

        while (matcher.find()) {
            // Add the matched placeholder to the list
            placeholders.add(matcher.group());
        }

        return placeholders;
    }
}
