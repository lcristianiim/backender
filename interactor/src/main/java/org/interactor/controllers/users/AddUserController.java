package org.interactor.controllers.users;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.interactor.internals.ObjectMapperSingleton;
import org.interactor.modules.datacenter.dtos.PersonDTO;
import org.interactor.modules.datacenter.PersonsPersistenceService;
import org.interactor.modules.logging.LoggerService;
import org.interactor.router.Controller;
import org.interactor.router.ReqContextDTO;
import org.interactor.router.RouterResponse;

import static org.interactor.router.ResponseType.JSON;

public class AddUserController implements Controller {
    LoggerService logger =  LoggerService.INSTANCE;
    private final PersonsPersistenceService personsPersistenceService = PersonsPersistenceService.INSTANCE;
    private String body;

    @Override
    public RouterResponse getResponse() {
        RouterResponse result = new RouterResponse();
        result.setType(JSON);

        try {
            PersonDTO person = ObjectMapperSingleton.INSTANCE.getObjectMapper().readValue(body, PersonDTO.class);

            try {
                personsPersistenceService.savePerson(person);
            } catch (RuntimeException e) {
                String errorMessage = "Failed to save person.";
                logger.getLogging().info(e.getMessage(), this.getClass());
                result.setCode(501);
                result.setBody(errorMessage);
                return result;
            }

            result.setCode(200);
            result.setBody("person saved");
        } catch (JsonProcessingException e) {
            result.setBody("Failed to save person with error: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public void initialize(ReqContextDTO controllerData, String registeredPath) {
        body = controllerData.getBody();
    }
}
