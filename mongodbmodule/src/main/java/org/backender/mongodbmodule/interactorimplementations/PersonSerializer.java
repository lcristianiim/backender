package org.backender.mongodbmodule.interactorimplementations;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.lang.reflect.Field;

public class PersonSerializer<T>  extends JsonSerializer<T> {
    private final ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public void serialize(T value, JsonGenerator gen, SerializerProvider serializerProvider) throws IOException {
        gen.writeStartObject(); // Start the JSON object

        // Use reflection to get all fields of the object
        Field[] fields = value.getClass().getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true); // Make private fields accessible
            try {
                // Write the field name and value to the JSON
                gen.writeObjectField(field.getName(), field.get(value));
            } catch (IllegalAccessException e) {
                // Handle the case where the field cannot be accessed
                e.printStackTrace();
            }
        }

        gen.writeEndObject(); // End the JSON object
    }
}
