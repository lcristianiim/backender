package org.interactor.configuration;

import org.interactor.controllers.TestController;
import org.interactor.controllers.UsersServiceController;
import org.interactor.controllers.users.AddUserController;
import org.interactor.modules.router.dtos.Controller;
import org.interactor.security.Role;

import java.util.*;

import static org.interactor.modules.router.dtos.RequestType.*;

public class RegisteredRoute {

    private static List<Route> registeredRoutes = List.of(
            new Route(GET, "users", new UsersServiceController(), List.of()),
            new Route(GET, "test-users", new UsersServiceController(), List.of(Role.ADMIN)),
            new Route(GET, "product/{id}?name&age", new TestController(), List.of()),
            new Route(POST, "add-user", new AddUserController(), List.of()));

    public static Map<String, Controller> getGETRoutes() {
        Map<String, Controller> result = new HashMap<>();
        registeredRoutes.stream()
                .filter(e -> e.requestType().equals(GET))
                .forEach(e -> result.put(e.path(), e.controller()));

        return result;
    }

    public static Map<String, Controller> getPOSTRoutes() {
        Map<String, Controller> result = new HashMap<>();
        registeredRoutes.stream()
                .filter(e -> e.requestType().equals(POST))
                .forEach(e -> result.put(e.path(), e.controller()));

        return result;
    }

    public static List<Route> getRegisteredRoutes() {
        return registeredRoutes;
    }

    // This was only added to be setup tests
    public static void setCustomRoutesForTests(List<Route> routes) {
        registeredRoutes = routes;
    }
}
