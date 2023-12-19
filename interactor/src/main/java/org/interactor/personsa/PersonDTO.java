package org.interactor.personsa;

import java.util.List;

public record PersonDTO(int id, String firstName, String lastName, List<AddressDTO> address) {}

