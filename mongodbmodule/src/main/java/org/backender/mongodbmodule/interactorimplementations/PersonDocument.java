package org.backender.mongodbmodule.interactorimplementations;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.bson.types.ObjectId;
import org.interactor.modules.datacenter.dtos.AddressDTO;

import java.util.List;

public class PersonDocument {
    @JsonProperty("_id")
    private ObjectId id;
    private String firstName;
    private String lastName;
    private List<AddressDTO> addresses;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
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
