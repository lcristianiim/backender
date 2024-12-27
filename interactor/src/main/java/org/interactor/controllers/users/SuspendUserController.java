package org.interactor.controllers.users;

import org.interactor.configuration.Route;
import org.interactor.modules.jwtauth.InputForUserSuspend;
import org.interactor.modules.jwtauth.JWTActionResponse;
import org.interactor.modules.jwtauth.JWTAuth;
import org.interactor.modules.router.dtos.Controller;
import org.interactor.modules.router.dtos.InteractorRequest;
import org.interactor.modules.router.dtos.InteractorResponse;

public class SuspendUserController implements Controller {
    JWTAuth jwtAuth;
    InputForUserSuspend input;

    @Override
    public InteractorResponse getResponse() {
        JWTActionResponse registrationResponse;

        try {
            registrationResponse = jwtAuth.suspend(input);
        } catch (Exception e) {
            throw new SomethingWentWrongCallingTheAuthServiceException(e.getMessage());
        }

        return evaluateRegistrationResponseAndCreateInteractorResponse(registrationResponse);
    }

    private InteractorResponse evaluateRegistrationResponseAndCreateInteractorResponse(
            JWTActionResponse confirmationResponse) {

//        if (confirmationResponse.isSuccess()) {
//            InteractorResponse response = new InteractorResponse();
//            response.setCode(200);
//            response.setBody("User has been confirmed.");
//            return response;
//        }
//
//        InteractorResponse response = new InteractorResponse();
//        response.setCode(500);
//        response.setBody("User has not been confirmed.");
//        return response;

        return null;
    }

    @Override
    public void initialize(InteractorRequest controllerData, Route registeredPath) {

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
