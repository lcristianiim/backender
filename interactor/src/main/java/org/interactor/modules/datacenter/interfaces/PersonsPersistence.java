package org.interactor.modules.datacenter.interfaces;

import org.interactor.modules.datacenter.dtos.PersonDTO;

import java.util.List;

public interface PersonsPersistence {
    void save(PersonDTO person);
    List<PersonDTO> findAll();
}
