package org.interactor.controllers.users;

import org.interactor.internals.Route;
import org.interactor.modules.jwtauth.JWTActionResponse;
import org.interactor.modules.jwtauth.JWTAuth;
import org.interactor.modules.jwtauth.JWTAuthService;
import org.interactor.modules.router.RouterService;
import org.interactor.modules.router.dtos.Controller;
import org.interactor.modules.router.dtos.InteractorRequest;
import org.interactor.modules.router.dtos.InteractorResponse;

import java.util.Map;

import static org.interactor.configuration.RegisteredRoute.IDENTIFIER;


public class PurgeUserController implements Controller {
    JWTAuth jwtAuth = JWTAuthService.INSTANCE.getJwtAuth();
    String identifierToPurge;

    @Override
    public InteractorResponse getResponse() {
        JWTActionResponse registrationResponse;

        try {
            registrationResponse = jwtAuth.purgeUser(identifierToPurge);
        } catch (Exception e) {
            throw new SomethingWentWrongCallingTheAuthServiceException(e.getMessage());
        }

        return evaluateRegistrationResponseAndCreateInteractorResponse(registrationResponse);
    }

    private InteractorResponse evaluateRegistrationResponseAndCreateInteractorResponse(
            JWTActionResponse confirmationResponse) {

        if (confirmationResponse.isSuccess()) {
            InteractorResponse response = new InteractorResponse();
            response.setCode(200);
            return response;
        }

        InteractorResponse response = new InteractorResponse();
        response.setCode(500);
        response.setBody(confirmationResponse.message());
        return response;
    }

    @Override
    public void initialize(InteractorRequest controllerData, Route registeredPath) {

        Map<String, String> pathParams = RouterService.INSTANCE.getRouter()
                .getPathParams(registeredPath.path(), controllerData.getRequestPath());

            identifierToPurge = pathParams.get(IDENTIFIER);
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
