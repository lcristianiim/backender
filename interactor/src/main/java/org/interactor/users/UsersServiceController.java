package org.interactor.users;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.interactor.modules.datacenter.PersonDTO;
import org.interactor.modules.datacenter.PersonsPersistenceService;
import org.interactor.router.Controller;
import org.interactor.router.ResponseBody;

import java.util.List;

public class UsersServiceController implements Controller {
    PersonsPersistenceService personsPersistenceService = PersonsPersistenceService.INSTANCE;

    @Override
    public ResponseBody getResponse() {
        String body = getResponseBody();
        return new ResponseBody(body, 200);
    }

    private String getResponseBody() {
        List<PersonDTO> persons = personsPersistenceService.getAllPersons();
        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.writeValueAsString(persons.getFirst());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
