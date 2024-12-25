package org.interactor.controllers.users;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.interactor.configuration.Route;
import org.interactor.internals.ObjectMapperSingleton;
import org.interactor.modules.router.dtos.Controller;
import org.interactor.modules.router.dtos.InteractorRequest;
import org.interactor.modules.router.dtos.InteractorResponse;
import org.interactor.usecases.users.InputForUserRegistration;

public class RegisterNewUserController implements Controller {
    InputForUserRegistration input;

    @Override
    public InteractorResponse getResponse() {
        return null;
    }

    @Override
    public void initialize(InteractorRequest controllerData, Route registeredPath) {

        try {
            input = ObjectMapperSingleton.INSTANCE.getObjectMapper()
                    .readValue(controllerData.getBody(), InputForUserRegistration.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
}
