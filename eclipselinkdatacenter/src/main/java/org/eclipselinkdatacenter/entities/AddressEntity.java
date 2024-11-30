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
@Table(name = "addresses")
public class AddressEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Basic(optional = false)
        @Column(name = "id")
	private Integer id;
	@Column(name = "street")
	private String street;
	@Column(name = "postal_code")
	private String postalCode;
	@JoinTable(name = "many_persons_has_many_addresses", joinColumns = {
        	@JoinColumn(name = "id_addresses", referencedColumnName = "id")}, inverseJoinColumns = {
        	@JoinColumn(name = "id_persons", referencedColumnName = "id")})
        @ManyToMany(cascade = CascadeType.ALL)
	private Collection<PersonEntity> personsCollection;

	public AddressEntity() {
	}

	public AddressEntity(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public Collection<PersonEntity> getPersonsCollection() {
		return personsCollection;
	}

	public void setPersonsCollection(List<PersonEntity> personsCollection) {
		this.personsCollection = personsCollection;
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
		if (!(object instanceof AddressEntity)) {
			return false;
		}
		AddressEntity other = (AddressEntity) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.mycompany.mavenproject1.Addresses[ id=" + id + " ]";
	}
	
}
