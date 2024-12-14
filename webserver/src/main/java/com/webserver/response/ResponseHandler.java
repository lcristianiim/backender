package com.webserver.response;

import io.javalin.http.Context;
import org.interactor.modules.router.dtos.RouterResponse;

// chain of responsibility pattern
public abstract class ResponseHandler {
    protected ResponseHandler nextHandler;

    public void setNextHandler(ResponseHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    public abstract void handleRequest(RouterResponse response, Context ctx);
}