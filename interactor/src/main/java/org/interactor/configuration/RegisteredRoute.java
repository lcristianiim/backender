package org.interactor.configuration;

import org.interactor.controllers.TestController;
import org.interactor.controllers.UsersServiceController;
import org.interactor.controllers.users.*;
import org.interactor.internals.Route;
import org.interactor.modules.router.dtos.RequestType;
import org.interactor.security.Role;

import java.util.*;

import static org.interactor.modules.router.dtos.RequestType.*;

public class RegisteredRoute {
    public static final String ACTIVATION_ID = "activation-id";
    public static final String IDENTIFIER = "identifier";

    private static List<Route> registeredRoutes = List.of(
            new Route(GET, "users", new UsersServiceController(), List.of()),
            new Route(GET, "test-users", new UsersServiceController(), List.of(Role.ADMIN)),
            new Route(GET, "product/{id}?name&age", new TestController(), List.of()),
//            new Route(POST, "users", new CreateUserController(), List.of()),
            new Route(POST, "auth/register", new RegisterUserController(), List.of()),
            new Route(GET, "auth/confirm/{%s}".formatted(ACTIVATION_ID), new ConfirmUserController(), List.of()),
            new Route(POST, "auth/decode", new DecodeUserController(), List.of()),
            new Route(POST, "auth/login", new LoginUserController(), List.of()),
            new Route(POST, "auth/refresh-token", new RefreshTokenController(), List.of()),
            new Route(POST, "auth/suspend-user", new RefreshTokenController(), List.of()),
            new Route(POST, "auth/unsuspend-user", new UnSuspendUserController(), List.of()),
            new Route(POST, "auth/cancel-user", new CancelUserController(), List.of()),
            new Route(GET, "users/get-link/{%s}".formatted(IDENTIFIER), new GetUserActivationLinkController(), List.of()),
            new Route(DELETE, "users/{%s}".formatted(IDENTIFIER), new PurgeUserController(), List.of()),
            new Route(POST, "auth/uncancel-user", new UnCancelUserController(), List.of()));


    public static List<Route> getRoutesByHttpMethod(RequestType type) {
        return registeredRoutes.stream()
                .filter(e -> e.requestType().equals(type))
                .toList();
    }

    public static List<Route> getRegisteredRoutes() {
        return registeredRoutes;
    }

    // This was only added to set up tests
    public static void setCustomRoutesForTests(List<Route> routes) {
        registeredRoutes = routes;
    }
}
