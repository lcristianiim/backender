package org.interactor.controllers.users;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.interactor.configuration.Route;
import org.interactor.internals.ObjectMapperSingleton;
import org.interactor.modules.jwtauth.*;
import org.interactor.modules.router.dtos.InteractorRequest;
import org.interactor.modules.router.dtos.InteractorResponse;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.interactor.modules.router.dtos.RequestType.POST;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RefreshTokenControllerTest {

    @Test
    void givenSuccessLoginResponse_ShouldReturn200() {
        ObjectMapper mapper = ObjectMapperSingleton.INSTANCE.getObjectMapper();

        JWTAuth jwtAuth = setupMockedJWTRefreshTokenToBeSuccessfull();

        RefreshTokenController controller = setupController(jwtAuth);
        Route route = new Route(
                POST, "auth/refresh-token", controller, List.of());

        String body = createInputForTokenRefresh(mapper);
        InteractorRequest request = setupTheRequest(body);

        controller.initialize(request, route);
        InteractorResponse result = controller.getResponse();

        assertEquals(200, result.getCode());
        assertEquals("{\"accessToken\":\"abcd\",\"refreshToken\":\"abcd-refresh\"}", result.getBody());
    }

    @Test
    void givenNotSuccessLoginResponse_ShouldReturn500() {
        ObjectMapper mapper = ObjectMapperSingleton.INSTANCE.getObjectMapper();

        JWTAuth jwtAuth = setupMockedJWTLoginToBeNotSuccessfull();

        RefreshTokenController controller = setupController(jwtAuth);
        Route route = new Route(
                POST, "auth/refresh-token", controller, List.of());

        String body = createInputForTokenRefresh(mapper);
        InteractorRequest request = setupTheRequest(body);

        controller.initialize(request,route);
        InteractorResponse result = controller.getResponse();

        assertEquals(500, result.getCode());
        assertEquals("refresh token request not successful", result.getBody());
    }

    private JWTAuth setupMockedJWTLoginToBeNotSuccessfull() {
        JWTActionResponseWithTokens response = new JWTActionResponseWithTokens(
                new JWTActionResponse(false, "refresh token request not successful"),
                null
        );
        JWTAuth jwtAuth = mock(JWTAuth.class);
        when(jwtAuth.refreshToken(any(RefreshTokenInput.class))).thenReturn(response);
        return jwtAuth;
    }

    private InteractorRequest setupTheRequest(String body) {
        InteractorRequest request = new InteractorRequest();
        request.setBody(body);
        return request;
    }

    private String createInputForTokenRefresh(ObjectMapper mapper) {
        RefreshTokenInput refreshTokenInput = new RefreshTokenInput("xyz", "abc");

        String body;
        try {
            body = mapper.writeValueAsString(refreshTokenInput);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return body;
    }


    private RefreshTokenController setupController(JWTAuth jwtAuth) {
        RefreshTokenController controller = new RefreshTokenController();
        controller.setJwtAuthForTesting(jwtAuth);
        return controller;
    }

    private JWTAuth setupMockedJWTRefreshTokenToBeSuccessfull() {
        JWTActionResponseWithTokens response = new JWTActionResponseWithTokens(
                new JWTActionResponse(true, "login successfull"),
                new JWTTokens("abcd", "abcd-refresh")
        );
        JWTAuth jwtAuth = mock(JWTAuth.class);
        when(jwtAuth.refreshToken(any(RefreshTokenInput.class))).thenReturn(response);
        return jwtAuth;
    }

}