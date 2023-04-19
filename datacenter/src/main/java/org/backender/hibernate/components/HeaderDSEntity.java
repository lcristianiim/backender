package org.backender.hibernate.components;

import jakarta.persistence.*;
import org.componenter.components.header.HeaderDS;

import java.io.Serializable;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "header_ds")
public class HeaderDSEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  long id;
    private String logo;
    @OneToMany(cascade = {CascadeType.ALL, CascadeType.REMOVE}, fetch = FetchType.EAGER, mappedBy = "header")
    @Column(name = "left_links")
    private Set<LinkEntity> leftLinks;
    @OneToMany(cascade = {CascadeType.ALL, CascadeType.REMOVE}, fetch = FetchType.EAGER, mappedBy = "header")
    @Column(name = "right_links")
    private Set<LinkEntity> rightLinks;

    public HeaderDSEntity() {
      // needed by hibernate
    }

    public HeaderDSEntity(long id, String logo, Set<LinkEntity> leftLinks, Set<LinkEntity> rightLinks) {
        this.id = id;
        this.logo = logo;
        this.leftLinks = leftLinks;
        this.rightLinks = rightLinks;
    }

    public static HeaderDSEntity dtoToEntity(HeaderDS dto) {
        HeaderDSEntity entity = new HeaderDSEntity();
        entity.setLogo(dto.logo());
        entity.setRightLinks(dto.rightLinks().stream().map(e -> LinkEntity.dtoToEntity(e, entity)).collect(Collectors.toSet()));
        entity.setLeftLinks(dto.leftLinks().stream().map(e -> LinkEntity.dtoToEntity(e, entity)).collect(Collectors.toSet()));
        return entity;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Set<LinkEntity> getLeftLinks() {
        return leftLinks;
    }

    public void setLeftLinks(Set<LinkEntity> leftLinks) {
        this.leftLinks = leftLinks;
    }

    public Set<LinkEntity> getRightLinks() {
        return rightLinks;
    }

    public void setRightLinks(Set<LinkEntity> rightLinks) {
        this.rightLinks = rightLinks;
    }
}
