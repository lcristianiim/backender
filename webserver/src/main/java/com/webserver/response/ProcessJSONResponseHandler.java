package com.webserver.response;

import io.javalin.http.Context;
import org.interactor.router.ResponseType;
import org.interactor.router.RouterResponse;

public class ProcessJSONResponseHandler extends ResponseHandler {

    @Override
    public void handleRequest(RouterResponse response, Context ctx) {

        if (response.getType().equals(ResponseType.JSON)) {
            ctx.status(response.getCode());
            ctx.json(response.getBody());

        } else if (nextHandler != null) {
            nextHandler.handleRequest(response, ctx);
        }
    }
}
