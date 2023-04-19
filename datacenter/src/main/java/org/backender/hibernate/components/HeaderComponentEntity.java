package org.backender.hibernate.components;

import jakarta.persistence.*;
import org.componenter.ComponentType;
import org.componenter.components.header.HeaderComponent;


@Entity
@Table(name = "header_component")
public class HeaderComponentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    private ComponentType type;

    @OneToOne(cascade = {CascadeType.ALL, CascadeType.REMOVE})
    private HeaderDSEntity data;

    public static HeaderComponentEntity dtoToEntity(HeaderComponent dto) {
        HeaderComponentEntity entity = new HeaderComponentEntity();

        entity.setData(HeaderDSEntity.dtoToEntity(dto.getData()));
        entity.setType(dto.getType());

        return entity;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ComponentType getType() {
        return type;
    }

    public HeaderDSEntity getData() {
        return data;
    }

    public void setData(HeaderDSEntity data) {
        this.data = data;
    }

    public void setType(ComponentType type) {
        this.type = type;
    }
}