package org.interactor.modules.datacenter.dtos;

import java.util.List;

public record PersonDTO(int id, String firstName, String lastName, List<AddressDTO> addresses) {}

