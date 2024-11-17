package org.interactor.router;

public interface Controller {
    RouterResponse getResponse();
    void initialize(ReqContextDTO controllerData, String registeredPath);
}
