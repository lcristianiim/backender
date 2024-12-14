package org.interactor.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.interactor.internals.ObjectMapperSingleton;
import org.interactor.modules.datacenter.dtos.PersonDTO;
import org.interactor.modules.datacenter.PersonsPersistenceService;
import org.interactor.modules.router.dtos.Controller;
import org.interactor.modules.router.dtos.ReqContextDTO;
import org.interactor.modules.router.dtos.RouterResponse;

import java.util.List;

import static org.interactor.modules.router.dtos.ResponseType.JSON;

public class UsersServiceController implements Controller {
    PersonsPersistenceService personsPersistenceService = PersonsPersistenceService.INSTANCE;

    @Override
    public RouterResponse getResponse() {
        String body = getResponseBody();

        RouterResponse result = new RouterResponse();
        result.setBody(body);
        result.setCode(200);
        result.setType(JSON);

        return result;
    }

    @Override
    public void initialize(ReqContextDTO ctx, String registeredPath) {
    }

    private String getResponseBody() {
        List<PersonDTO> persons = personsPersistenceService.getAllPersons();

        try {
            return ObjectMapperSingleton.INSTANCE.getObjectMapper().writeValueAsString(persons);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
