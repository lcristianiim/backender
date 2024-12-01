package org.eclipselinkdatacenter.persistence.mappers;

import org.eclipselinkdatacenter.persistence.entities.AddressEntity;
import org.eclipselinkdatacenter.persistence.entities.PersonEntity;
import org.interactor.modules.datacenter.dtos.AddressDTO;
import org.interactor.modules.datacenter.dtos.PersonDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface PersonMapper {
    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);

    @Mapping(target = "addressesCollection", expression = "java(mapAddresses(personDTO.addresses(), personEntity))")
    PersonEntity toEntity(PersonDTO personDTO);

    @Mapping(target = "addresses", source = "addressesCollection")
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
            if (addressEntity.getPersonsCollection() == null)
                addressEntity.setPersonsCollection(new ArrayList<>());

            addressEntity.getPersonsCollection().add(personEntity); // Set the person reference
            addressEntities.add(addressEntity);
        }
        return addressEntities;
    }
}
