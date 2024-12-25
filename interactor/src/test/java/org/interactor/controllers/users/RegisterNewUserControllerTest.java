package org.interactor.controllers.users;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.interactor.configuration.Route;
import org.interactor.internals.ObjectMapperSingleton;
import org.interactor.modules.router.dtos.InteractorRequest;
import org.interactor.modules.router.dtos.InteractorResponse;
import org.interactor.routes.InteractorEntry;
import org.interactor.usecases.users.InputForUserRegistration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.interactor.configuration.RegisteredRoute.setCustomRoutesForTests;
import static org.interactor.modules.router.dtos.RequestType.POST;

class RegisterNewUserControllerTest {


    @Test
    @Disabled
    void a() {
        ObjectMapper mapper = ObjectMapperSingleton.INSTANCE.getObjectMapper();

        setCustomRoutesForTests(Collections.singletonList(new Route(
                POST, "register-user", new RegisterNewUserController(), List.of())));

        InputForUserRegistration inputForUserRegistration = new InputForUserRegistration(
                "John", "john@email.com", "1234", "1234",
                true, true
        );

        String body;
        try {
            body = mapper.writeValueAsString(inputForUserRegistration);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        InteractorRequest request = new InteractorRequest();
        request.setRequestType(POST);
        request.setBody(body);
        request.setRequestPath("register-user");

        InteractorEntry entry = new InteractorEntry();
        InteractorResponse result = entry.processRequest(request);

        Assertions.assertNotNull(result);
    }
}