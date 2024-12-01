package org.eclipselinkdatacenter.persistence.mappers;

import org.eclipselinkdatacenter.persistence.entities.AddressEntity;
import org.interactor.modules.datacenter.dtos.AddressDTO;

public interface AddressMapper {
    AddressDTO toDTO(AddressEntity addressEntity);
    AddressEntity toEntity(AddressDTO addressDTO);
}
