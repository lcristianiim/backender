package org.interactor.modules.router;

import org.interactor.modules.router.dtos.ReqContextDTO;
import org.interactor.modules.router.dtos.RouterResponse;

import java.util.Map;

public interface Router {

    /**
     * This is the link from webserver to the interactor module for all GET requests
     * <p>
     * the controllerResolver decides what controller to use and then returns the response
     * @param ctx this is the all the request context
     * @return Always ResponseBody is the object that is returned by Controllers by convention
     *
     */
    RouterResponse get(ReqContextDTO ctx);

    /**
     * This is the link from webserver to the interactor module for all POST requests
     * <p>
     * the controllerResolver decides what controller to use and then returns the response
     * @param ctx this is the all the request context
     * @return Always ResponseBody is the object that is returned by Controllers by convention
     *
     */
    RouterResponse post(ReqContextDTO ctx);

    Map<String, String> getPathParams(String registeredPath, String path);
    Map<String, String> getQueryParams(String registeredPath, String requestPath);
}
