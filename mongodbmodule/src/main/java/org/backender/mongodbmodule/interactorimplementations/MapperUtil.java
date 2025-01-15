package org.backender.mongodbmodule.interactorimplementations;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import de.undercouch.bson4jackson.BsonModule;
import org.bson.Document;
import org.interactor.modules.datacenter.dtos.PersonDTO;

public class MapperUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new BsonModule())
            .registerModule(createModule())
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public static Document toDocument(Object obj) {
        try {
            return Document.parse(objectMapper.writeValueAsString(obj));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T fromDocument(Document document, Class<T> clazz) {
        try {
            return objectMapper.readValue(document.toJson(), clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private static SimpleModule createModule() {
        SimpleModule module = new SimpleModule();
//        module.addSerializer(Object.class, new PersonSerializer());
        module.addDeserializer(PersonDTO.class, new CustomDeserializer());
        return module;
    }
}
