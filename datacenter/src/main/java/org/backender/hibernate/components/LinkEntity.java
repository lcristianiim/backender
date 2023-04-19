package org.backender.hibernate.components;

import jakarta.persistence.*;
import org.componenter.components.commons.Link;

import java.io.Serializable;

@Entity
@Table(name = "link")
public class LinkEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String url;

    @ManyToOne
    @JoinColumn(name = "header_id")
    private HeaderDSEntity header;

    public LinkEntity() {
        // no arg constructor for hibernate
    }

    public static LinkEntity dtoToEntity(Link dto) {
        return new LinkEntity(dto.name(), dto.url());
    }

    public static LinkEntity dtoToEntity(Link dto, HeaderDSEntity ds) {
        var link = new LinkEntity(dto.name(), dto.url());
        link.setHeader(ds);
        return link;
    }

    public LinkEntity(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public HeaderDSEntity getHeader() {
        return header;
    }

    public void setHeader(HeaderDSEntity header) {
        this.header = header;
    }

}
