package org.interactor.controllers.persons;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.interactor.configuration.Route;
import org.interactor.internals.ObjectMapperSingleton;
import org.interactor.modules.datacenter.dtos.PersonDTO;
import org.interactor.modules.datacenter.PersonsPersistenceService;
import org.interactor.modules.logging.LoggerService;
import org.interactor.modules.router.dtos.Controller;
import org.interactor.modules.router.dtos.InteractorRequest;
import org.interactor.modules.router.dtos.InteractorResponse;

import static org.interactor.modules.router.dtos.ResponseType.JSON;

public class AddPersonController implements Controller {
    LoggerService logger =  LoggerService.INSTANCE;
    private final PersonsPersistenceService personsPersistenceService = PersonsPersistenceService.INSTANCE;
    private String body;

    @Override
    public InteractorResponse getResponse() {
        InteractorResponse result = new InteractorResponse();
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
    public void initialize(InteractorRequest controllerData, Route registeredPath) {
        body = controllerData.getBody();
    }
}
