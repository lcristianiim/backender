/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.eclipselinkdatacenter.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author cristian
 */
@Entity
@Table(name = "persons")
public class PersonEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Basic(optional = false)
        @Column(name = "id")
	private Integer id;
	@Column(name = "first_name")
	private String firstName;
	@Column(name = "last_name")
	private String lastName;
	@ManyToMany(mappedBy = "personsCollection", cascade = CascadeType.ALL)
	private Collection<AddressEntity> addressesCollection;

	public PersonEntity() {
	}

	public PersonEntity(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	public Collection<AddressEntity> getAddressesCollection() {
		return addressesCollection;
	}

	public void setAddressesCollection(List<AddressEntity> addressesCollection) {
		this.addressesCollection = addressesCollection;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof PersonEntity)) {
			return false;
		}
		PersonEntity other = (PersonEntity) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.mycompany.mavenproject1.Persons[ id=" + id + " ]";
	}
	
}
