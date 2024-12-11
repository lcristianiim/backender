package org.backender.interactorimplementations;

import org.bson.types.ObjectId;
import org.interactor.modules.datacenter.dtos.PersonDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PersonMapper {
    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);

    PersonDocument toEntity(PersonDTO personDTO);
    PersonDTO toDTO(PersonDocument PersonDocument);

    default ObjectId map(String value) {
        if (value == null)
            return null;

        return new ObjectId(value); // Adjust this logic as needed
    }

    default String map(ObjectId value) {
        return value.toString();
    }
}
