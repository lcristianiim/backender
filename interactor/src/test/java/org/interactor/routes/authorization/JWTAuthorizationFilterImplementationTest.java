package org.interactor.routes.authorization;

import org.interactor.modules.jwtauth.JWTActionResponse;
import org.interactor.modules.jwtauth.JWTActionResponseWithPrincipal;
import org.interactor.modules.jwtauth.JWTActionResponseWithTokens;
import org.interactor.modules.jwtauth.JWTAuth;
import org.interactor.modules.router.dtos.InteractorRequest;
import org.interactor.modules.router.dtos.Principal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class JWTAuthorizationFilterImplementationTest {

    @Test
    void givenValidToken_ShouldReturnPrincipalAndNotExecuteNextHandler() {
        String principalName = "John";
        String token = "xyztoken";

        JWTAuth jwtAuth = mock(JWTAuth.class);
        Principal mockedPrincipal = new Principal.Builder()
                .setName(principalName)
                .build();

        JWTActionResponse jwtActionResponse = new JWTActionResponse(true, "");
        JWTActionResponseWithPrincipal response = new JWTActionResponseWithPrincipal(jwtActionResponse, mockedPrincipal);
        when(jwtAuth.decode(token)).thenReturn(response);

        AuthorizationMechanismFilter nextHandler = mock(JWTAuthorizationFilterImplementation.class);

        JWTAuthorizationFilterImplementation jwtAuthorization = new JWTAuthorizationFilterImplementation();
        jwtAuthorization.setJwtAuth(jwtAuth);
        jwtAuthorization.setNextHandler(nextHandler);


        InteractorRequest ctx = new InteractorRequest();
        ctx.setAuthorization("Bearer %s".formatted(token));
        Optional<Principal> principal = jwtAuthorization.execute(ctx);

        assertEquals(principalName, principal.get().name());
        verify(nextHandler, times(0)).execute(any(InteractorRequest.class));
    }

    @Test
    void givenInvalidAuthenticationAndNextHandlerNotNull_ShouldTriggerNextHandler() {
        String token = "xyztoken";

        AuthorizationMechanismFilter nextHandler = mock(JWTAuthorizationFilterImplementation.class);

        Principal principalFromNext = new Principal.Builder()
                .setName("Mike")
                .build();
        when(nextHandler.execute(any(InteractorRequest.class))).thenReturn(Optional.of(principalFromNext));

        JWTAuthorizationFilterImplementation jwtAuthorization = new JWTAuthorizationFilterImplementation();
        jwtAuthorization.setNextHandler(nextHandler);

        InteractorRequest ctx = new InteractorRequest();
        ctx.setAuthorization("Invalid %s".formatted(token));
        Optional<Principal> principal = jwtAuthorization.execute(ctx);

        assertEquals("Mike", principal.get().name());
        verify(nextHandler, times(1)).execute(any(InteractorRequest.class));
    }

    @Test
    void givenInvalidAuthenticationAndNextHandlerNull_ShouldReturnEmptyOptional() {
        String token = "xyztoken";

        JWTAuthorizationFilterImplementation jwtAuthorization = new JWTAuthorizationFilterImplementation();

        InteractorRequest ctx = new InteractorRequest();
        ctx.setAuthorization("InvalidBearerString %s".formatted(token));
        Optional<Principal> principal = jwtAuthorization.execute(ctx);

        assertTrue(principal.isEmpty());
    }

    @Test
    void givenNullAuthorization_ShouldReturnEmptyPrincipal() {

        JWTAuthorizationFilterImplementation jwtAuthorization = new JWTAuthorizationFilterImplementation();

        InteractorRequest ctx = new InteractorRequest();
        Optional<Principal> principal = jwtAuthorization.execute(ctx);

        assertTrue(principal.isEmpty());
    }


    @Test
    void givenInvalidAuthenticationByHavingMoreThanOneSpace_ShouldReturnEmptyOptional() {
        String token = "xyztoken";

        JWTAuthorizationFilterImplementation jwtAuthorization = new JWTAuthorizationFilterImplementation();

        InteractorRequest ctx = new InteractorRequest();
        ctx.setAuthorization("InvalidBearerString %s anotherInvalidString".formatted(token));
        Optional<Principal> principal = jwtAuthorization.execute(ctx);

        assertTrue(principal.isEmpty());
    }

}