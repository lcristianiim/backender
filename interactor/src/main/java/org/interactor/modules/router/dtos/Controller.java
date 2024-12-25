package org.interactor.modules.router.dtos;

import org.interactor.configuration.Route;

public interface Controller {
    InteractorResponse getResponse();
    void initialize(InteractorRequest controllerData, Route registeredRoute);
}
