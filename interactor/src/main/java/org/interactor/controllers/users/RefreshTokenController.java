package org.interactor.controllers.users;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.interactor.configuration.Route;
import org.interactor.internals.ObjectMapperSingleton;
import org.interactor.modules.jwtauth.JWTActionResponseWithTokens;
import org.interactor.modules.jwtauth.JWTAuth;
import org.interactor.modules.jwtauth.RefreshTokenInput;
import org.interactor.modules.router.dtos.Controller;
import org.interactor.modules.router.dtos.InteractorRequest;
import org.interactor.modules.router.dtos.InteractorResponse;

public class RefreshTokenController implements Controller {
    JWTAuth jwtAuth;
    RefreshTokenInput input;

    @Override
    public InteractorResponse getResponse() {
        JWTActionResponseWithTokens response;

        try {
            response = jwtAuth.refreshToken(input);
        } catch (Exception e) {
            throw new SomethingWentWrongCallingTheAuthServiceException(e.getMessage());
        }

        return evaluateRegistrationResponseAndCreateInteractorResponse(response);
    }

    private InteractorResponse evaluateRegistrationResponseAndCreateInteractorResponse(
            JWTActionResponseWithTokens refreshTokens) {

        if (refreshTokens.jwtActionResponse().isSuccess()) {
            String tokensAsJson;
            try {
                tokensAsJson = ObjectMapperSingleton.INSTANCE.getObjectMapper()
                        .writeValueAsString(refreshTokens.JWTTokens());
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            InteractorResponse response = new InteractorResponse();
            response.setCode(200);
            response.setBody(tokensAsJson);
            return response;
        }

        InteractorResponse response = new InteractorResponse();
        response.setBody(refreshTokens.jwtActionResponse().message());
        response.setCode(500);

        return response;
    }

    @Override
    public void initialize(InteractorRequest controllerData, Route registeredPath) {
        try {
            input = ObjectMapperSingleton.INSTANCE.getObjectMapper().readValue(controllerData.getBody(), RefreshTokenInput.class);
        } catch (JsonProcessingException e) {
            throw new CouldNotParseLoginInputException(e.getMessage());
        }
    }

    public static class SomethingWentWrongCallingTheAuthServiceException extends RuntimeException {
        public SomethingWentWrongCallingTheAuthServiceException(String message) {
            super(message);
        }
    }

    public static class CouldNotParseLoginInputException extends RuntimeException {
        public CouldNotParseLoginInputException(String message) {
            super(message);
        }
    }

    public void setJwtAuthForTesting(JWTAuth jwtAuth) {
        this.jwtAuth = jwtAuth;
    }
}
