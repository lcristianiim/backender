package org.interactor.configuration;

import org.interactor.controllers.TestController;
import org.interactor.controllers.UsersServiceController;
import org.interactor.controllers.persons.AddPersonController;
import org.interactor.controllers.users.RegisterNewUserController;
import org.interactor.modules.router.dtos.RequestType;
import org.interactor.security.Role;

import java.util.*;

import static org.interactor.modules.router.dtos.RequestType.*;

public class RegisteredRoute {

    private static List<Route> registeredRoutes = List.of(
            new Route(GET, "users", new UsersServiceController(), List.of()),
            new Route(GET, "test-users", new UsersServiceController(), List.of(Role.ADMIN)),
            new Route(GET, "product/{id}?name&age", new TestController(), List.of()),
            new Route(POST, "add-user", new AddPersonController(), List.of()),
            new Route(POST, "register-user", new RegisterNewUserController(), List.of()));

    public static List<Route> getRoutesByHttpMethod(RequestType type) {
        return registeredRoutes.stream()
                .filter(e -> e.requestType().equals(type))
                .toList();
    }

    public static List<Route> getRegisteredRoutes() {
        return registeredRoutes;
    }

    // This was only added to be setup tests
    public static void setCustomRoutesForTests(List<Route> routes) {
        registeredRoutes = routes;
    }
}
