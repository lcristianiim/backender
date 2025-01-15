package org.interactor.controllers.users;

import org.interactor.internals.Route;
import org.interactor.modules.jwtauth.JWTActionResponse;
import org.interactor.modules.jwtauth.JWTAuth;
import org.interactor.modules.router.dtos.InteractorRequest;
import org.interactor.modules.router.dtos.InteractorResponse;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.interactor.configuration.RegisteredRoute.ACTIVATION_ID;
import static org.interactor.modules.router.dtos.RequestType.POST;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ConfirmUserControllerTest {

    @Test
    void givenJWTAuthResponseWithSuccessRegistration_ShouldReturnInteractorResponseWithCode200() {
        JWTAuth jwtAuth = setupMockedJWTConfirmToHaveSuccess();
        ConfirmUserController controller = setupController(jwtAuth);

        Route route = new Route(
                POST, "something/{%s}".formatted(ACTIVATION_ID), controller, List.of());

        InteractorRequest request = setupTheRequest();

        controller.initialize(request, route);
        InteractorResponse result = controller.getResponse();

        assertEquals(200, result.getCode());
        assertFalse(result.getBody().isEmpty());
    }

    @Test
    void givenJWTAuthResponseWithSuccessRegistration_ShouldReturnInteractorResponseWithCode500() {
        JWTAuth jwtAuth = setupMockedJWTConfirmToNotHaveSuccess();
        ConfirmUserController controller = setupController(jwtAuth);

        Route route = new Route(
                POST, "something/{%s}".formatted(ACTIVATION_ID), controller, List.of());

        InteractorRequest request = setupTheRequest();

        controller.initialize(request, route);
        InteractorResponse result = controller.getResponse();

        assertEquals(500, result.getCode());
        assertFalse(result.getBody().isEmpty());
    }

    @Test
    void givenExceptionByTheJWTAuthModule_ShouldThrowCustomException() {
        JWTAuth jwtAuth = setupMockedJWTConfirmToThrowException();
        ConfirmUserController controller = setupController(jwtAuth);

        Route route = new Route(
                POST, "something/{%s}".formatted(ACTIVATION_ID), controller, List.of());

        InteractorRequest request = setupTheRequest();

        controller.initialize(request, route);

        assertThrows(ConfirmUserController.SomethingWentWrongCallingTheAuthServiceException.class,
                controller::getResponse);
    }

    private JWTAuth setupMockedJWTConfirmToThrowException() {
        JWTAuth jwtAuth = mock(JWTAuth.class);
        when(jwtAuth.confirm(anyString())).thenThrow(new RuntimeException("some exception"));
        return jwtAuth;
    }

    private InteractorRequest setupTheRequest() {
        InteractorRequest request = new InteractorRequest();
        request.setRequestType(POST);
        request.setRequestPath("something/1234");
        return request;
    }

    private ConfirmUserController setupController(JWTAuth jwtAuth) {
        ConfirmUserController controller = new ConfirmUserController();
        controller.setJwtAuthForTesting(jwtAuth);
        return controller;
    }

    private JWTAuth setupMockedJWTConfirmToHaveSuccess() {
        JWTAuth jwtAuth = mock(JWTAuth.class);
        JWTActionResponse jwtActionResponse = new JWTActionResponse(true, "irrelevant for test");
        when(jwtAuth.confirm(any(String.class))).thenReturn(jwtActionResponse);
        return jwtAuth;
    }

    private JWTAuth setupMockedJWTConfirmToNotHaveSuccess() {
        JWTAuth jwtAuth = mock(JWTAuth.class);
        JWTActionResponse jwtActionResponse = new JWTActionResponse(false, "irrelevant for test");
        when(jwtAuth.confirm(any(String.class))).thenReturn(jwtActionResponse);
        return jwtAuth;
    }

}