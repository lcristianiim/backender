package org.interactor.controllers.users;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.interactor.internals.Route;
import org.interactor.internals.ObjectMapperSingleton;
import org.interactor.modules.jwtauth.JWTActionResponse;
import org.interactor.modules.jwtauth.JWTAuth;
import org.interactor.modules.router.dtos.Controller;
import org.interactor.modules.router.dtos.InteractorRequest;
import org.interactor.modules.router.dtos.InteractorResponse;

public class UnSuspendUserController implements Controller {
    JWTAuth jwtAuth;
    String input;

    @Override
    public InteractorResponse getResponse() {
        JWTActionResponse response;

        try {
            response = jwtAuth.unSuspend(input);
        } catch (Exception e) {
            throw new SomethingWentWrongCallingTheAuthServiceException(e.getMessage());
        }

        return evaluateRegistrationResponseAndCreateInteractorResponse(response);
    }

    private InteractorResponse evaluateRegistrationResponseAndCreateInteractorResponse(
            JWTActionResponse confirmationResponse) {

        if (confirmationResponse.isSuccess()) {
            InteractorResponse response = new InteractorResponse();
            response.setCode(200);
            response.setBody("User has been unsuspended.");
            return response;
        }

        InteractorResponse response = new InteractorResponse();
        response.setCode(500);
        response.setBody("User has not been unsuspended.");
        return response;
    }

    @Override
    public void initialize(InteractorRequest controllerData, Route registeredPath) {
        try {
            input = ObjectMapperSingleton.INSTANCE.getObjectMapper().readValue(controllerData.getBody(), String.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static class SomethingWentWrongCallingTheAuthServiceException extends RuntimeException {
        public SomethingWentWrongCallingTheAuthServiceException(String message) {
            super(message);
        }
    }


    public void setJwtAuthForTesting(JWTAuth jwtAuth) {
        this.jwtAuth = jwtAuth;
    }

}
