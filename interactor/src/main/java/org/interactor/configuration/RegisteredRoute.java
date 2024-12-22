package org.interactor.configuration;

import org.interactor.controllers.TestController;
import org.interactor.controllers.UsersServiceController;
import org.interactor.controllers.users.AddUserController;
import org.interactor.modules.router.dtos.Controller;
import org.interactor.modules.router.dtos.RequestType;
import org.interactor.security.Role;

import java.util.*;

import static org.interactor.modules.router.dtos.RequestType.*;

public enum RegisteredRoute {
    USERS(GET, "users", new UsersServiceController(), List.of()),
    TEST_USERS(GET, "test-users", new UsersServiceController(), List.of(Role.ADMIN)),
    PRODUCT(GET, "product/{id}?name&age", new TestController(), List.of()),
    ADD_USER(POST, "add-user", new AddUserController(), List.of());

    private final RequestType requestType;
    private final String path;
    private final Controller controller;
    private final List<Role> roles;

    RegisteredRoute(RequestType requestType, String path, Controller controller, List<Role> roles) {
        this.requestType = requestType;
        this.path = path;
        this.controller = controller;
        this.roles = roles;
    }

    public static Map<String, Controller> getGETRoutes() {
        Map<String, Controller> result = new HashMap<>();
        Arrays.stream(RegisteredRoute.values())
                .filter(e -> e.requestType.equals(GET))
                .forEach(e -> result.put(e.getPath(), e.getController()));

        return result;
    }

    public static Map<String, Controller> getPOSTRoutes() {
        Map<String, Controller> result = new HashMap<>();
        Arrays.stream(RegisteredRoute.values())
                .filter(e -> e.requestType.equals(POST))
                .forEach(e -> result.put(e.getPath(), e.getController()));

        return result;
    }

    public String getPath() {
        return path;
    }

    public Controller getController() {
        return controller;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public List<Role> getRoles() {
        return roles;
    }
}
