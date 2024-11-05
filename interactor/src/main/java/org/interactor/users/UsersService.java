package org.interactor.users;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.interactor.modules.datacenter.AddressDTO;
import org.interactor.modules.datacenter.PersonDTO;
import org.interactor.router.Controller;
import org.interactor.router.ResponseBody;

import java.util.List;

public class UsersService implements Controller {

    public String getResponseBody() {
        ObjectMapper mapper = new ObjectMapper();
        PersonDTO personDTO = new PersonDTO(123, "John", "Joe",
                List.of((new AddressDTO(10, "Washington Street", 123456))));

        try {
            return mapper.writeValueAsString(personDTO);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public ResponseBody getResponse() {
        String body = getResponseBody();
        return new ResponseBody(body, 200);
    }
}
