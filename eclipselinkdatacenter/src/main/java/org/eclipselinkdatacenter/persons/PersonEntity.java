package org.eclipselinkdatacenter.persons;

import jakarta.persistence.*;

import java.util.List;


@Entity(name = PersonEntity.TABLE_NAME)
public class PersonEntity {

    public static final String TABLE_NAME = "persons";
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String PERSON_ID_MAPPING = "person_id";


    @Id
    @GeneratedValue
    private int id;

    @Column(name = FIRST_NAME)
    private String firstName;

    @Column(name = LAST_NAME)
    private String lastName;

    @OneToMany(mappedBy = "person", cascade = {CascadeType.ALL, CascadeType.REMOVE})
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
