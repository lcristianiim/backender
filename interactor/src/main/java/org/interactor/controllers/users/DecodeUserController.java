package org.interactor.controllers.users;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.interactor.internals.Route;
import org.interactor.internals.ObjectMapperSingleton;
import org.interactor.modules.jwtauth.JWTActionResponseWithPrincipal;
import org.interactor.modules.jwtauth.JWTAuth;
import org.interactor.modules.jwtauth.JWTAuthService;
import org.interactor.modules.router.dtos.Controller;
import org.interactor.modules.router.dtos.InteractorRequest;
import org.interactor.modules.router.dtos.InteractorResponse;

public class DecodeUserController implements Controller {
    JWTAuth jwtAuth = JWTAuthService.INSTANCE.getJwtAuth();
    DecoderInput input;

    @Override
    public InteractorResponse getResponse() {
        JWTActionResponseWithPrincipal response;

        try {
            response = jwtAuth.decode(input.token());
        } catch (Exception e) {
            throw new SomethingWentWrongCallingTheAuthServiceException(e.getMessage());
        }

        return evaluateRegistrationResponseAndCreateInteractorResponse(response);
    }

    private InteractorResponse evaluateRegistrationResponseAndCreateInteractorResponse(
            JWTActionResponseWithPrincipal loginResponse) {

        if (loginResponse.jwtActionResponse().isSuccess()) {
            String principal;
            try {
                principal = ObjectMapperSingleton.INSTANCE.getObjectMapper()
                        .writeValueAsString(loginResponse.principal());
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            InteractorResponse response = new InteractorResponse();
            response.setCode(200);
            response.setBody(principal);
            return response;
        }

        InteractorResponse response = new InteractorResponse();
        response.setBody(loginResponse.jwtActionResponse().message());
        response.setCode(500);

        return response;
    }

    @Override
    public void initialize(InteractorRequest controllerData, Route registeredPath) {
        try {
            input = ObjectMapperSingleton.INSTANCE.getObjectMapper().readValue(controllerData.getBody(), DecoderInput.class);
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

    public record DecoderInput(String token) {}
}
