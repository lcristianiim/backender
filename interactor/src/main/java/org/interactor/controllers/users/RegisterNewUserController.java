package org.interactor.controllers.users;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.interactor.configuration.Route;
import org.interactor.internals.ObjectMapperSingleton;
import org.interactor.modules.jwtauth.JWTActionResponse;
import org.interactor.modules.jwtauth.JWTAuth;
import org.interactor.modules.jwtauth.InputForUserRegistration;
import org.interactor.modules.router.dtos.Controller;
import org.interactor.modules.router.dtos.InteractorRequest;
import org.interactor.modules.router.dtos.InteractorResponse;

public class RegisterNewUserController implements Controller {
    JWTAuth jwtAuth;
    InputForUserRegistration input;

    @Override
    public InteractorResponse getResponse() {
        JWTActionResponse registrationResponse;

        try {
            registrationResponse = jwtAuth.register(input);
        } catch (Exception e) {
            throw new SomethingWentWrongCallingTheAuthServiceException(e.getMessage());
        }

        return evaluateRegistrationResponseAndCreateInteractorResponse(registrationResponse);
    }

    private InteractorResponse evaluateRegistrationResponseAndCreateInteractorResponse(JWTActionResponse registrationResponse) {
        if (registrationResponse.isSuccess()) {
            InteractorResponse response = new InteractorResponse();
            response.setCode(200);
            return response;
        }

        InteractorResponse response = new InteractorResponse();
        response.setBody(registrationResponse.message());
        response.setCode(500);

        return response;
    }

    @Override
    public void initialize(InteractorRequest controllerData, Route registeredPath) {
        try {
            input = ObjectMapperSingleton.INSTANCE.getObjectMapper()
                    .readValue(controllerData.getBody(), InputForUserRegistration.class);

        } catch (JsonProcessingException e) {
            throw new CouldNotParseInputForUserRegistrationException(e.getMessage());
        }
    }

    private static class CouldNotParseInputForUserRegistrationException extends RuntimeException {
        public CouldNotParseInputForUserRegistrationException(String message) {
            super(message);
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
