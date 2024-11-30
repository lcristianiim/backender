package org.eclipselinkdatacenter.entities;

import jakarta.persistence.*;

import static org.eclipselinkdatacenter.internal.CommonTablesConfiguration.*;
import static org.eclipselinkdatacenter.entities.AddressEntity.TABLE_NAME;

@Entity(name = TABLE_NAME)
public class AddressEntity {
    public static final String TABLE_NAME = "addresses";
    public static final String SEQ_GENERATOR = TABLE_NAME + SEQUENCE_GENERATOR_SUFFIX;
    public static final String SEQ_NAME = TABLE_NAME + SEQUENCE_NAME_SUFFIX;
    public static final String ADDRESS = "address";
    public static final String POSTAL_CODE = "postal_code";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_GENERATOR)
    @SequenceGenerator(name = SEQ_GENERATOR, sequenceName = SEQ_NAME, allocationSize = INCREMENT_ALLOCATION_SIZE)
    private int id;

    @Column(name = ADDRESS)
    private String address;

    @Column(name = POSTAL_CODE)
    private int postalCode;

    @ManyToOne
    @JoinColumn (name = PersonEntity.PERSON_ID_MAPPING, nullable = false)
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

    public PersonEntity getPerson() {
        return person;
    }

    public void setPerson(PersonEntity person) {
        this.person = person;
    }
}
