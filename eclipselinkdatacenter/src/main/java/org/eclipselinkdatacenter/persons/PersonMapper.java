package org.eclipselinkdatacenter.persons;

import org.interactor.modules.datacenter.AddressDTO;
import org.interactor.modules.datacenter.PersonDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface PersonMapper {
    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);

    @Mapping(target = "addresses", expression = "java(mapAddresses(personDTO.addresses(), personEntity))")
    PersonEntity toEntity(PersonDTO personDTO);

    PersonDTO toDTO(PersonEntity personEntity);
    AddressEntity toEntity(AddressDTO addressDTO);
    AddressDTO toDTO(AddressEntity addressEntity);

    default List<AddressEntity> mapAddresses(List<AddressDTO> addressDTOs, PersonEntity personEntity) {
        if (addressDTOs == null) {
            return null;
        }
        List<AddressEntity> addressEntities = new ArrayList<>();
        for (AddressDTO addressDTO : addressDTOs) {
            AddressEntity addressEntity = toEntity(addressDTO);
            addressEntity.setPerson(personEntity); // Set the person reference
            addressEntities.add(addressEntity);
        }
        return addressEntities;
    }
}
