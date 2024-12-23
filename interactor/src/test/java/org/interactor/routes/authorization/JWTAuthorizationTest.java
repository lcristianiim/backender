package org.interactor.routes.authorization;

import org.interactor.modules.jwtauth.JWTAuth;
import org.interactor.modules.router.dtos.InteractorRequest;
import org.interactor.modules.router.dtos.Principal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JWTAuthorizationTest {


    @Test
    void givenValidToken_ShouldReturnPrincipal() {
        String principalName = "John";
        String token = "xyztoken";

        JWTAuth jwtAuth = mock(JWTAuth.class);
        Principal mockedPrincipal = new Principal.Builder()
                .setName(principalName)
                        .build();
        when(jwtAuth.decode(token)).thenReturn(mockedPrincipal);

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
}