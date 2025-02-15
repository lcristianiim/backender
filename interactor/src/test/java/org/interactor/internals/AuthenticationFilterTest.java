package org.interactor.internals;

import org.interactor.configuration.RegisteredRoute;
import org.interactor.controllers.UsersServiceController;
import org.interactor.modules.router.dtos.InteractorRequest;
import org.interactor.modules.router.dtos.InteractorResponse;
import org.interactor.routes.chain.AuthenticationFilter;
import org.interactor.routes.chain.RequestFilter;
import org.interactor.security.Role;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.interactor.modules.router.dtos.RequestType.GET;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationFilterTest {

    @Test
    void givenInvalidRoute_ShouldExecuteTheRouteFilterThatShouldReturn404() {
        RequestFilter nextFilter = mock(RequestFilter.class);
        InteractorResponse responseOfNextFilter = new InteractorResponse();
        responseOfNextFilter.setCode(404);
        when(nextFilter.execute(any(InteractorRequest.class))).thenReturn(responseOfNextFilter);

        AuthenticationFilter filter = new AuthenticationFilter();
        filter.setNextHandler(nextFilter);

        InteractorRequest ctx = new InteractorRequest();
        ctx.setRequestPath("invalid");
        ctx.setRequestType(GET);

        RegisteredRoute.setCustomRoutesForTests(List.of(
                new Route(GET, "test-users", new UsersServiceController(), List.of(Role.ADMIN))
        ));

        InteractorResponse response = filter.execute(ctx);

        assertEquals(404, response.getCode());
    }

    @Test
    void givenValidRouteThatNeedsNoAuthorization_ShouldPassItToTheNextHandler() {
        RequestFilter nextFilter = mock(RequestFilter.class);
        InteractorResponse responseOfNextFilter = new InteractorResponse();
        responseOfNextFilter.setCode(222);
        when(nextFilter.execute(any(InteractorRequest.class))).thenReturn(responseOfNextFilter);

        AuthenticationFilter filter = new AuthenticationFilter();
        filter.setNextHandler(nextFilter);

        InteractorRequest ctx = new InteractorRequest();
        ctx.setRequestPath("test-users");
        ctx.setRequestType(GET);

        RegisteredRoute.setCustomRoutesForTests(List.of(
                new Route(GET, "test-users", new UsersServiceController(), List.of())
        ));

        InteractorResponse response = filter.execute(ctx);

        assertEquals(222, response.getCode());
    }

    @Test
    void givenValidRouteThatNeedsNoAuthorizationAndNoNextHandlerSet_ShouldReturn503() {
        AuthenticationFilter filter = new AuthenticationFilter();

        InteractorRequest ctx = new InteractorRequest();
        ctx.setRequestPath("test-users");
        ctx.setRequestType(GET);

        RegisteredRoute.setCustomRoutesForTests(List.of(
                new Route(GET, "test-users", new UsersServiceController(), List.of())
        ));

        InteractorResponse response = filter.execute(ctx);

        assertEquals(503, response.getCode());
    }

    @Test
    void givenValidRouteThatNeedsAuthorizationButProvideNullAuthorization_ShouldReturn403() {
        AuthenticationFilter filter = new AuthenticationFilter();
        InteractorRequest ctx = new InteractorRequest();
        ctx.setRequestPath("test-users");
        ctx.setRequestType(GET);

        RegisteredRoute.setCustomRoutesForTests(List.of(
                new Route(GET, "test-users", new UsersServiceController(), List.of(Role.ADMIN))
        ));

        InteractorResponse response = filter.execute(ctx);

        assertEquals(403, response.getCode());
    }

    @Test
    void givenValidRouteThatNeedsAuthorizationButProvideEmptyAuthorization_ShouldReturn403() {
        AuthenticationFilter filter = new AuthenticationFilter();
        InteractorRequest ctx = new InteractorRequest();
        ctx.setRequestPath("test-users");
        ctx.setRequestType(GET);
        ctx.setAuthorization("");

        RegisteredRoute.setCustomRoutesForTests(List.of(
                new Route(GET, "test-users", new UsersServiceController(), List.of(Role.ADMIN))
        ));

        InteractorResponse response = filter.execute(ctx);

        assertEquals(403, response.getCode());
    }

    @Test
    void givenValidRouteWithAuthorizationRequired_ShouldExecuteTheNextFilter() {
        RequestFilter customTestingFilter = new RequestFilter() {
            @Override
            public void setNextHandler(RequestFilter nextHandler) {
                // irrelevant for this test
            }

            @Override
            public InteractorResponse execute(InteractorRequest ctx) {
                InteractorResponse response = new InteractorResponse();
                response.setCode(111);
                return response;
            }
        };

        AuthenticationFilter filter = new AuthenticationFilter();
        filter.setNextHandler(customTestingFilter);
        InteractorRequest ctx = new InteractorRequest();
        ctx.setRequestPath("test-users");
        ctx.setRequestType(GET);
        ctx.setAuthorization("autorization mechanism");

        RegisteredRoute.setCustomRoutesForTests(List.of(
                new Route(GET, "test-users", new UsersServiceController(), List.of(Role.ADMIN))
        ));

        InteractorResponse response = filter.execute(ctx);

        assertEquals(111, response.getCode());
    }

    @Test
    void givenValidRouteWithAuthorizationRequiredAndNoNextFilter_ShouldReturnInvalidResponse() {
        AuthenticationFilter filter = new AuthenticationFilter();
        InteractorRequest ctx = new InteractorRequest();
        ctx.setRequestPath("test-users");
        ctx.setRequestType(GET);
        ctx.setAuthorization("autorization mechanism");

        RegisteredRoute.setCustomRoutesForTests(List.of(
                new Route(GET, "test-users", new UsersServiceController(), List.of(Role.ADMIN))
        ));

        InteractorResponse response = filter.execute(ctx);

        assertEquals(503, response.getCode());
    }

}