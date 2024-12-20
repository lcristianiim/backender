package org.interactor.modules.router.dtos;

public interface Controller {
    InteractorResponse getResponse();
    void initialize(InteractorRequest controllerData, String registeredPath);
}
