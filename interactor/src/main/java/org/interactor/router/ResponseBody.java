package org.interactor.router;

public record ResponseBody (
        String body,
        int code
) {}
