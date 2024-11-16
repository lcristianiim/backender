package org.interactor.router;

import java.util.Locale;

public class ReqContextDTO {
    String requestPath;
    Locale locale;
    String body;

    public String getRequestPath() {
        return requestPath;
    }

    public void setRequestPath(String requestPath) {
        this.requestPath = requestPath;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
};
