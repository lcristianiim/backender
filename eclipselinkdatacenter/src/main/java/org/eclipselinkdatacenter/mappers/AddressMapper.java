package org.eclipselinkdatacenter.mappers;

import org.eclipselinkdatacenter.entities.AddressEntity;
import org.interactor.modules.datacenter.dtos.AddressDTO;

public interface AddressMapper {
    AddressDTO toDTO(AddressEntity addressEntity);
    AddressEntity toEntity(AddressDTO addressDTO);
}
