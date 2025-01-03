package org.interactor.modules.datacenter.dtos;

import java.util.List;

public class PersonDTO {
    private String id;
    private String firstName;
    String lastName;
    List<AddressDTO> addresses;

    public PersonDTO() {
        // this is needed for Jackson
    }

    public PersonDTO(String id, String firstName, String lastName, List<AddressDTO> addresses) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.addresses = addresses;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<AddressDTO> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<AddressDTO> addresses) {
        this.addresses = addresses;
    }
}

