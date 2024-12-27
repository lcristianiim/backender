package org.interactor.controllers.users;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.interactor.configuration.Route;
import org.interactor.internals.ObjectMapperSingleton;
import org.interactor.modules.jwtauth.InputForUserSuspend;
import org.interactor.modules.jwtauth.JWTActionResponse;
import org.interactor.modules.jwtauth.JWTAuth;
import org.interactor.modules.router.dtos.InteractorRequest;
import org.interactor.modules.router.dtos.InteractorResponse;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.interactor.modules.router.dtos.RequestType.POST;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SuspendUserControllerTest {

    @Test
    void givenJWTAuthResponseWithSuccessRegistration_ShouldReturnInteractorResponseWithCode200() {
        ObjectMapper mapper = ObjectMapperSingleton.INSTANCE.getObjectMapper();

        JWTAuth jwtAuth = setupMockedJWTRegisterToAlwaysReturnTrue();
        SuspendUserController controller = setupController(jwtAuth);
        Route route = new Route(
                POST, "auth/suspend-user", controller, List.of());

        String body = createInputForUserSuspendValidForRegistration(mapper);
        InteractorRequest request = setupTheRequest(body);

        controller.initialize(request, route);
        InteractorResponse result = controller.getResponse();

        assertEquals(200, result.getCode());
    }

    @Test
    void givenJWTAuthResponseWithNonSuccessRegistration_ShouldReturnInteractorResponseWithCode500AndReturnErrorMessageInBody() {
        ObjectMapper mapper = ObjectMapperSingleton.INSTANCE.getObjectMapper();

        JWTAuth jwtAuth = setupMockedJWTRegisterToAlwaysReturnFalse();
        SuspendUserController controller = setupController(jwtAuth);
        Route route = new Route(
                POST, "auth/suspend-user", controller, List.of());

        String body = createInputForUserSuspendValidForRegistration(mapper);
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
        SuspendUserController controller = setupController(jwtAuth);
        Route route = new Route(
                POST, "auth/suspend-user", controller, List.of());

        String body = createInputForUserSuspendValidForRegistration(mapper);
        InteractorRequest request = setupTheRequest(body);

        controller.initialize(request, route);
        InteractorResponse result = controller.getResponse();

        assertEquals(500, result.getCode());
        assertEquals("failed", result.getBody());
    }

    private JWTAuth setupMockedJWTRegisterToThrowException() {
        JWTAuth jwtAuth = mock(JWTAuth.class);
        when(jwtAuth.suspend(any(InputForUserSuspend.class)))
                .thenThrow(ConfirmUserController.SomethingWentWrongCallingTheAuthServiceException.class);
        return jwtAuth;
    }

    private JWTAuth setupMockedJWTRegisterToAlwaysReturnFalse() {
        JWTAuth jwtAuth = mock(JWTAuth.class);
        JWTActionResponse jwtActionResponse = new JWTActionResponse(false, "failed");
        when(jwtAuth.suspend(any(InputForUserSuspend.class))).thenReturn(jwtActionResponse);
        return jwtAuth;
    }

    private JWTAuth setupMockedJWTRegisterToAlwaysReturnTrue() {
        JWTAuth jwtAuth = mock(JWTAuth.class);
        JWTActionResponse jwtActionResponse = new JWTActionResponse(true, "");
        when(jwtAuth.suspend(any(InputForUserSuspend.class))).thenReturn(jwtActionResponse);
        return jwtAuth;
    }

    private SuspendUserController setupController(JWTAuth jwtAuth) {
        SuspendUserController controller = new SuspendUserController();
        controller.setJwtAuthForTesting(jwtAuth);
        return controller;
    }

    private InteractorRequest setupTheRequest(String body) {
        InteractorRequest request = new InteractorRequest();
        request.setBody(body);
        return request;
    }

    private static String createInputForUserSuspendValidForRegistration(ObjectMapper mapper) {
        InputForUserSuspend inputForUserSuspend = new InputForUserSuspend(
                "abc-123",
                "1704117895",
                "1705068295"
        );

        String body;
        try {
            body = mapper.writeValueAsString(inputForUserSuspend);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return body;
    }
}