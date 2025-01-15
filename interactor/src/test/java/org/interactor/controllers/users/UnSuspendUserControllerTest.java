package org.interactor.controllers.users;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.interactor.internals.Route;
import org.interactor.internals.ObjectMapperSingleton;
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

class UnSuspendUserControllerTest {

    @Test
    void givenJWTAuthResponseWithSuccessRegistration_ShouldReturnInteractorResponseWithCode200() {
        ObjectMapper mapper = ObjectMapperSingleton.INSTANCE.getObjectMapper();

        JWTAuth jwtAuth = setupMockedJWTRegisterToAlwaysReturnTrue();
        UnSuspendUserController controller = setupController(jwtAuth);
        Route route = new Route(
                POST, "auth/unsuspend-user", controller, List.of());

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
        UnSuspendUserController controller = setupController(jwtAuth);
        Route route = new Route(
                POST, "auth/unsuspend-user", controller, List.of());

        String body = createInputForUserSuspendValidForRegistration(mapper);
        InteractorRequest request = setupTheRequest(body);

        controller.initialize(request, route);
        InteractorResponse result = controller.getResponse();

        assertEquals(500, result.getCode());
        assertEquals("User has not been unsuspended.", result.getBody());
    }

    @Test
    void givenExceptionFromJWTAuth_ShouldThrowCustomException() {
        ObjectMapper mapper = ObjectMapperSingleton.INSTANCE.getObjectMapper();

        JWTAuth jwtAuth = setupMockedJWTRegisterToThrowException();
        UnSuspendUserController controller = setupController(jwtAuth);
        Route route = new Route(
                POST, "auth/suspend-user", controller, List.of());

        String body = createInputForUserSuspendValidForRegistration(mapper);
        InteractorRequest request = setupTheRequest(body);

        controller.initialize(request, route);

        assertThrows(UnSuspendUserController.SomethingWentWrongCallingTheAuthServiceException.class,
                controller::getResponse);
    }

    private JWTAuth setupMockedJWTRegisterToThrowException() {
        JWTAuth jwtAuth = mock(JWTAuth.class);
        when(jwtAuth.unSuspend(any(String.class)))
                .thenThrow(ConfirmUserController.SomethingWentWrongCallingTheAuthServiceException.class);
        return jwtAuth;
    }

    private JWTAuth setupMockedJWTRegisterToAlwaysReturnFalse() {
        JWTAuth jwtAuth = mock(JWTAuth.class);
        JWTActionResponse jwtActionResponse = new JWTActionResponse(false, "failed");
        when(jwtAuth.unSuspend(any(String.class))).thenReturn(jwtActionResponse);
        return jwtAuth;
    }

    private JWTAuth setupMockedJWTRegisterToAlwaysReturnTrue() {
        JWTAuth jwtAuth = mock(JWTAuth.class);
        JWTActionResponse jwtActionResponse = new JWTActionResponse(true, "");
        when(jwtAuth.unSuspend(any(String.class))).thenReturn(jwtActionResponse);
        return jwtAuth;
    }

    private UnSuspendUserController setupController(JWTAuth jwtAuth) {
        UnSuspendUserController controller = new UnSuspendUserController();
        controller.setJwtAuthForTesting(jwtAuth);
        return controller;
    }

    private InteractorRequest setupTheRequest(String body) {
        InteractorRequest request = new InteractorRequest();
        request.setBody(body);
        return request;
    }

    private static String createInputForUserSuspendValidForRegistration(ObjectMapper mapper) {
        String inputForUserUnsuspended = "1234-abc";

        String body;
        try {
            body = mapper.writeValueAsString(inputForUserUnsuspended);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return body;
    }
}