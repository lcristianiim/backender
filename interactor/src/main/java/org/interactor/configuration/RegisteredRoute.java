package org.interactor.configuration;

import org.interactor.controllers.TestController;
import org.interactor.controllers.UsersServiceController;
import org.interactor.controllers.users.AddUserController;
import org.interactor.modules.router.dtos.Controller;
import org.interactor.modules.router.dtos.RequestType;

import java.util.*;

import static org.interactor.modules.router.dtos.RequestType.*;

public enum RegisteredRoute {
    USERS(GET, "users", new UsersServiceController()),
    TEST_USERS(GET, "test-users", new UsersServiceController()),
    PRODUCT(GET, "product/{id}?name&age", new TestController()),
    ADD_USER(POST, "add-user", new AddUserController());

    private final RequestType requestType;
    private final String path;
    private final Controller controller;

    RegisteredRoute(RequestType requestType, String path, Controller controller) {
        this.requestType = requestType;
        this.path = path;
        this.controller = controller;
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
}
