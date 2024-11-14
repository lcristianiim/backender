package org.interactor.router;

public interface Controller {
    ResponseBody getResponse();
    void initialize(RequestContext controllerData);
}
