package org.backender.hibernate;

import jakarta.persistence.*;
import org.backender.hibernate.components.HeaderComponentEntity;
import org.componenter.Page;
import org.componenter.Theme;
import org.componenter.components.header.HeaderComponent;

import java.util.List;

@Entity
@Table(name = "page")
public class PageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String title;
    private Theme theme;
    private String url;
    private String view;

    @ManyToMany(cascade = {CascadeType.ALL, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    public List<HeaderComponentEntity> components;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public List<HeaderComponentEntity> getComponents() {
        return components;
    }

    public void setComponents(List<HeaderComponentEntity> components) {
        this.components = components;
    }

    public static PageEntity dtoToEntity(Page dto) {
        PageEntity entity = new PageEntity();

        entity.setTitle(dto.getTitle());
        entity.setUrl(dto.getUrl());
        entity.setView(dto.getView());
        entity.setTheme(dto.getTheme());

        entity.setComponents(dto.getComponents().stream().map(e -> HeaderComponentEntity.dtoToEntity((HeaderComponent) e)).toList());

        return entity;
    }
}
