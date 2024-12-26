package org.interactor.controllers.users;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.interactor.configuration.Route;
import org.interactor.internals.ObjectMapperSingleton;
import org.interactor.modules.jwtauth.InputForUserRegistration;
import org.interactor.modules.jwtauth.JWTActionResponse;
import org.interactor.modules.jwtauth.JWTAuth;
import org.interactor.modules.router.dtos.InteractorRequest;
import org.interactor.modules.router.dtos.InteractorResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.interactor.modules.router.dtos.RequestType.POST;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegisterNewUserControllerTest {

    @Test
    void givenJWTAuthResponseWithSuccessRegistration_ShouldReturnInteractorResponseWithCode200() {
        ObjectMapper mapper = ObjectMapperSingleton.INSTANCE.getObjectMapper();

        JWTAuth jwtAuth = setupMockedJWTRegisterToAlwaysReturnTrue();
        RegisterNewUserController controller = setupController(jwtAuth);
        Route route = new Route(
                POST, "register-user", controller, List.of());

        String body = createInputForUserRegistrationValidForRegistration(mapper);
        InteractorRequest request = setupTheRequest(body);

        controller.initialize(request, route);
        InteractorResponse result = controller.getResponse();

        assertEquals(200, result.getCode());
    }

    @Test
    void givenJWTAuthResponseWithNonSuccessRegistration_ShouldReturnInteractorResponseWithCode500AndReturnErrorMessageInBody() {
        ObjectMapper mapper = ObjectMapperSingleton.INSTANCE.getObjectMapper();

        JWTAuth jwtAuth = setupMockedJWTRegisterToAlwaysReturnFalse();
        RegisterNewUserController controller = setupController(jwtAuth);
        Route route = new Route(
                POST, "register-user", controller, List.of());

        String body = createInputForUserRegistrationValidForRegistration(mapper);
        InteractorRequest request = setupTheRequest(body);

        controller.initialize(request, route);
        InteractorResponse result = controller.getResponse();

        assertEquals(500, result.getCode());
        assertEquals("failed", result.getBody());
    }

    @Test
    void givenExceptionFromJWTAuth_ShouldThrowCustomException() {
        ObjectMapper mapper = ObjectMapperSingleton.INSTANCE.getObjectMapper();

        JWTAuth jwtAuth = setupMockedJWTRegisterToThrowException();
        RegisterNewUserController controller = setupController(jwtAuth);
        Route route = new Route(
                POST, "register-user", controller, List.of());

        String body = createInputForUserRegistrationValidForRegistration(mapper);
        InteractorRequest request = setupTheRequest(body);

        controller.initialize(request, route);
        InteractorResponse result = controller.getResponse();

        assertEquals(500, result.getCode());
        assertEquals("failed", result.getBody());
    }

    private JWTAuth setupMockedJWTRegisterToThrowException() {
        JWTAuth jwtAuth = mock(JWTAuth.class);
        when(jwtAuth.register(any(InputForUserRegistration.class)))
                .thenThrow(ConfirmUserController.SomethingWentWrongCallingTheAuthServiceException.class);
        return jwtAuth;
    }

    private JWTAuth setupMockedJWTRegisterToAlwaysReturnFalse() {
        JWTAuth jwtAuth = mock(JWTAuth.class);
        JWTActionResponse jwtActionResponse = new JWTActionResponse(false, "failed");
        when(jwtAuth.register(any(InputForUserRegistration.class))).thenReturn(jwtActionResponse);
        return jwtAuth;
    }

    private JWTAuth setupMockedJWTRegisterToAlwaysReturnTrue() {
        JWTAuth jwtAuth = mock(JWTAuth.class);
        JWTActionResponse jwtActionResponse = new JWTActionResponse(true, "");
        when(jwtAuth.register(any(InputForUserRegistration.class))).thenReturn(jwtActionResponse);
        return jwtAuth;
    }

    private RegisterNewUserController setupController(JWTAuth jwtAuth) {
        RegisterNewUserController controller = new RegisterNewUserController();
        controller.setJwtAuthForTesting(jwtAuth);
        return controller;
    }

    private InteractorRequest setupTheRequest(String body) {
        InteractorRequest request = new InteractorRequest();
        request.setBody(body);
        return request;
    }

    private static String createInputForUserRegistrationValidForRegistration(ObjectMapper mapper) {
        InputForUserRegistration inputForUserRegistration = new InputForUserRegistration(
                "John", "john@email.com", "1234", "1234",
                true, true
        );

        String body;
        try {
            body = mapper.writeValueAsString(inputForUserRegistration);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return body;
    }
}