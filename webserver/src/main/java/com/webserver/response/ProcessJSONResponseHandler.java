package com.webserver.response;

import io.javalin.http.Context;
import org.interactor.modules.router.dtos.ResponseType;
import org.interactor.modules.router.dtos.InteractorResponse;

public class ProcessJSONResponseHandler extends ResponseHandler {

    @Override
    public void handleRequest(InteractorResponse response, Context ctx) {

        if (response.getType().equals(ResponseType.JSON)) {
            ctx.status(response.getCode());
            ctx.json(response.getBody());

        } else if (nextHandler != null) {
            nextHandler.handleRequest(response, ctx);
        }
    }
}
