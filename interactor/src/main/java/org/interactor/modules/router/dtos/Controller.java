package org.interactor.modules.router.dtos;

public interface Controller {
    RouterResponse getResponse();
    void initialize(ReqContextDTO controllerData, String registeredPath);
}
