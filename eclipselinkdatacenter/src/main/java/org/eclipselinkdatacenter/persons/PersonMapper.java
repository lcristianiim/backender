package org.eclipselinkdatacenter.persons;

import org.interactor.modules.datacenter.AddressDTO;
import org.interactor.modules.datacenter.PersonDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PersonMapper {
    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);

    PersonEntity toEntity(PersonDTO personDTO);
    PersonDTO toDTO(PersonEntity personEntity);
    AddressEntity toEntity(AddressDTO addressDTO);
    AddressDTO toDTO(AddressEntity addressEntity);
}
