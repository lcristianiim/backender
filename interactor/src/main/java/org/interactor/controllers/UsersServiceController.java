package org.interactor.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.interactor.modules.datacenter.PersonDTO;
import org.interactor.modules.datacenter.PersonsPersistenceService;
import org.interactor.router.Controller;
import org.interactor.router.ReqContextDTO;
import org.interactor.router.RouterResponse;

import java.util.List;

import static org.interactor.router.ResponseType.JSON;

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
        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.writeValueAsString(persons);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
