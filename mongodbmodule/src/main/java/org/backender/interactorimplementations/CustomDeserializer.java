package org.backender.interactorimplementations;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.interactor.modules.datacenter.dtos.PersonDTO;

import java.io.IOException;

public class CustomDeserializer<T>  extends JsonDeserializer<T> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public T deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        JsonNode node = objectMapper.readTree(jsonParser);
        PersonDTO myObject = new PersonDTO();

        if (node.has("_id")) {
            JsonNode idNode = node.get("_id");
            if (idNode.has("$oid")) {
                myObject.setId(idNode.get("$oid").asText());
            }
        }

        ObjectNode objectNode = (ObjectNode) node;
        objectNode.remove("_id");

        objectMapper.readerForUpdating(myObject).readValue(objectNode);

        return (T) myObject;
    }
}

//    @Override
//    public T deserialize(JsonParser p, DeserializationContext deserializationContext) throws IOException, JacksonException {
//
//        ObjectMapper mapper = (ObjectMapper) p.getCodec();
//        JsonNode node = mapper.readTree(p);
//
//        // Determine the class type to deserialize
//        Class<?> clazz = deserializationContext.getContextualType().getRawClass();
//
//        // Create a new instance of the target class
//        Object instance;
//        try {
//            instance = clazz.getDeclaredConstructor().newInstance();
//        } catch (Exception e) {
//            throw new IOException("Failed to create an instance of " + clazz.getName(), e);
//        }
//
//        // Map the _id field to id
//        if (node.has("_id")) {
//            String id = node.get("_id").asText();
//            try {
//                Field idField = clazz.getDeclaredField("id");
//                idField.setAccessible(true);
//                idField.set(instance, id);
//            } catch (NoSuchFieldException | IllegalAccessException e) {
//                throw new IOException("Failed to set field 'id' on " + clazz.getName(), e);
//            }
//        }
//
//        // Use ObjectMapper to map the rest of the fields
//        mapper.readerForUpdating(instance).readValue(node);
//
//        return (T) instance;
//    }
