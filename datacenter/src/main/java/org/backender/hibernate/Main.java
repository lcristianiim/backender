package org.backender.hibernate;

import org.backender.hibernate.components.HeaderDSEntity;
import org.componenter.Page;
import org.componenter.Theme;
import org.componenter.components.Component;
import org.componenter.components.commons.Link;
import org.componenter.components.header.HeaderComponent;
import org.componenter.components.header.HeaderDS;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        HeaderDS data = new HeaderDS("Great Logo",
                List.of(
                        new Link("First link", "page-one"),
                        new Link("Second link", "page-two")),
                List.of(
                        new Link("x", "y"),
                        new Link("z", "q"))
        );

        Component header = new HeaderComponent(data);

        Page page = new Page("Mata", Theme.LIGHT, "test-a", "indexx.html", List.of(header));
        PageEntity entity = PageEntity.dtoToEntity(page);

//        PersistenceService<PageEntity> pagePs = new PersistenceService<>(PageEntity.class);
//        pagePs.savePage(entity);

        PersistenceService<HeaderDSEntity> headerPs = new PersistenceService<>(HeaderDSEntity.class);
        var me = headerPs.findPage(1);

        System.out.println("hello");
    }
}