package org.interactor.modules.router;

import org.interactor.configuration.RegisteredRoute;
import org.interactor.modules.router.dtos.InteractorRequest;
import org.interactor.modules.router.dtos.InteractorResponse;
import org.interactor.modules.router.dtos.RequestType;

import java.util.Map;
import java.util.Optional;

public interface Router {

    /**
     * This is the link from webserver to the interactor module for all GET requests
     * <p>
     * the controllerResolver decides what controller to use and then returns the response
     * @param ctx this is the all the request context
     * @return Always ResponseBody is the object that is returned by Controllers by convention
     *
     */
    InteractorResponse processRequest(InteractorRequest ctx);

    Optional<RegisteredRoute> getRegisteredRoute(String path, RequestType requestType);
    Map<String, String> getPathParams(String registeredPath, String path);
    Map<String, String> getQueryParams(String registeredPath, String requestPath);
}
