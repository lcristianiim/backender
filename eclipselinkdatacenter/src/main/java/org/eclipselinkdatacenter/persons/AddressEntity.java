package org.eclipselinkdatacenter.persons;

import jakarta.persistence.*;

import static org.eclipselinkdatacenter.persons.AddressEntity.TABLE_NAME;

@Entity(name = TABLE_NAME)
public class AddressEntity {
    public static final String TABLE_NAME = "addresses";
    public static final String ADDRESS = "addresses";
    public static final String POSTAL_CODE = "postal_code";

    @Id
    @GeneratedValue
    private int id;

    @Column(name = ADDRESS)
    private String address;

    @Column(name = POSTAL_CODE)
    private int postalCode;

    @ManyToOne
    @JoinColumn (name = PersonEntity.PERSON_ID_MAPPING)
    private PersonEntity person;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public PersonEntity getPersonId() {
        return person;
    }

    public void setPersonId(PersonEntity personId) {
        this.person = personId;
    }
}
