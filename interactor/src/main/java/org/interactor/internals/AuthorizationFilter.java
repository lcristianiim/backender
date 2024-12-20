package org.interactor.internals;

import org.interactor.modules.router.dtos.InteractorRequest;
import org.interactor.modules.router.dtos.InteractorResponse;
import org.interactor.modules.router.dtos.ResponseType;

public class AuthorizationFilter implements RequestFilter {
    private RequestFilter nextFilter;

    @Override
    public void setNextHandler(RequestFilter nextFilter) {
        this.nextFilter = nextFilter;
    }

    @Override
    public InteractorResponse execute(InteractorRequest ctx) {

        return nextFilter.execute(ctx);
        // do checks and return bad response if something goes wrong
//        if (ctx.getAuthorization() == null) {
//            return createInvalidResponse();

            // all ok so pass to next
//        } else if (nextFilter != null) {
//            nextFilter.execute(ctx);
//        }

//        return requestNotHandled();

    }

    private InteractorResponse requestNotHandled() {
        InteractorResponse response = new InteractorResponse();
        response.setCode(503);
        response.setBody("The request was not handled by the Authorization Filter");
        response.setType(ResponseType.JSON);
        return response;
    }

    private InteractorResponse createInvalidResponse() {
        InteractorResponse response = new InteractorResponse();
        response.setCode(503);
        response.setBody("Authorization failed");
        response.setType(ResponseType.JSON);
        return response;
    }
}
