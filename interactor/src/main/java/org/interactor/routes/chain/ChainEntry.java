package org.interactor.routes.chain;

import org.interactor.modules.router.dtos.InteractorRequest;
import org.interactor.modules.router.dtos.InteractorResponse;
import org.interactor.modules.router.dtos.ResponseType;

public class ChainEntry {
    private RequestFilter firstHandler;

    public void setFirstHandler(RequestFilter handler) {
        this.firstHandler = handler;
    }

    public InteractorResponse handleRequest(InteractorRequest request) {
        if (firstHandler != null) {
            return firstHandler.execute(request);
        }
        return createNoFiltersResponse();
    }

    private InteractorResponse createNoFiltersResponse() {
        InteractorResponse response = new InteractorResponse();
        response.setCode(501);
        response.setBody("Internal server error related with request filters");
        response.setType(ResponseType.JSON);
        return response;
    }
}
