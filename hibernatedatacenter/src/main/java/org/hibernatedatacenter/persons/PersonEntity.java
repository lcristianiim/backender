package org.hibernatedatacenter.persons;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class PersonEntity {
    @Id
    @GeneratedValue
    private int id;
    private String firstName;
    private String lastName;
    @OneToMany(cascade = {CascadeType.ALL, CascadeType.REMOVE})
    private List<AddressEntity> addresses = new java.util.ArrayList<>();

    public void setAddresses(List<AddressEntity> addresses) {
        this.addresses = addresses;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public List<AddressEntity> getAddresses() {
        return addresses;
    }
}
