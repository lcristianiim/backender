package org.interactor.users;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.interactor.modules.datacenter.PersonDTO;
import org.interactor.modules.datacenter.PersonsPersistenceService;
import org.interactor.router.Controller;
import org.interactor.router.RequestContext;
import org.interactor.router.ResponseBody;

import java.util.List;

import static org.interactor.router.ResponseType.JSON;

public class UsersServiceController implements Controller {
    PersonsPersistenceService personsPersistenceService = PersonsPersistenceService.INSTANCE;
    RequestContext ctx;


    @Override
    public ResponseBody getResponse() {
        String body = getResponseBody();

        ResponseBody result = new ResponseBody();
        result.setBody(body);
        result.setCode(200);
        result.setType(JSON);

        return result;
    }

    @Override
    public void initialize(RequestContext ctx) {
        this.ctx = ctx;
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
