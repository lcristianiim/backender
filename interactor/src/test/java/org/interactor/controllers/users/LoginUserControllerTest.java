package org.interactor.controllers.users;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.interactor.internals.Route;
import org.interactor.internals.ObjectMapperSingleton;
import org.interactor.modules.jwtauth.*;
import org.interactor.modules.router.dtos.InteractorRequest;
import org.interactor.modules.router.dtos.InteractorResponse;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.interactor.modules.router.dtos.RequestType.POST;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LoginUserControllerTest {

    @Test
    void givenSuccessLoginResponse_ShouldReturn200() {
        ObjectMapper mapper = ObjectMapperSingleton.INSTANCE.getObjectMapper();

        JWTAuth jwtAuth = setupMockedJWTLoginToBeSuccessfull();

        LoginUserController controller = setupController(jwtAuth);
        Route route = new Route(
                POST, "auth/login", controller, List.of());

        String body = createInputForUserLogin(mapper);
        InteractorRequest request = setupTheRequest(body);

        controller.initialize(request,route);
        InteractorResponse result = controller.getResponse();

        assertEquals(200, result.getCode());
        assertEquals("{\"accessToken\":\"access-token\",\"refreshToken\":\"refresh-token\"}", result.getBody());
    }

    @Test
    void givenNotSuccessLoginResponse_ShouldReturn500() {
        ObjectMapper mapper = ObjectMapperSingleton.INSTANCE.getObjectMapper();

        JWTAuth jwtAuth = setupMockedJWTLoginToBeNotSuccessfull();

        LoginUserController controller = setupController(jwtAuth);
        Route route = new Route(
                POST, "auth/login", controller, List.of());

        String body = createInputForUserLogin(mapper);
        InteractorRequest request = setupTheRequest(body);

        controller.initialize(request,route);
        InteractorResponse result = controller.getResponse();

        assertEquals(500, result.getCode());
        assertEquals("login not successful", result.getBody());
    }

    private JWTAuth setupMockedJWTLoginToBeNotSuccessfull() {
        JWTActionResponseWithTokens response = new JWTActionResponseWithTokens(
                new JWTActionResponse(false, "login not successful"),
                null
        );
        JWTAuth jwtAuth = mock(JWTAuth.class);
        when(jwtAuth.login(any(LoginInput.class))).thenReturn(response);
        return jwtAuth;
    }

    private InteractorRequest setupTheRequest(String body) {
        InteractorRequest request = new InteractorRequest();
        request.setBody(body);
        return request;
    }

    private String createInputForUserLogin(ObjectMapper mapper) {
        LoginInput loginInput = new LoginInput("john@email.com", "1234");

        String body;
        try {
            body = mapper.writeValueAsString(loginInput);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return body;
    }


    private LoginUserController setupController(JWTAuth jwtAuth) {
        LoginUserController controller = new LoginUserController();
        controller.setJwtAuthForTesting(jwtAuth);
        return controller;
    }

    private JWTAuth setupMockedJWTLoginToBeSuccessfull() {
        JWTActionResponseWithTokens response = new JWTActionResponseWithTokens(
                new JWTActionResponse(true, "login successfull"),
                new JWTTokens("access-token", "refresh-token")
        );
        JWTAuth jwtAuth = mock(JWTAuth.class);
        when(jwtAuth.login(any(LoginInput.class))).thenReturn(response);
        return jwtAuth;
    }
}