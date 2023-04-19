package org.backender.mongo;

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
                        new Link("Third link", "page-tree"),
                        new Link("Forth link", "page-four"))
        );

        Component header = new HeaderComponent(data);

        MongoRepo componentsRepo = new MongoRepo(Component.class, "components");
        MongoRepo pagesRepo = new MongoRepo(Page.class, "pages");

        Page page = new Page("Cool title", Theme.LIGHT, "test-a", "index.html", List.of(header));
        componentsRepo.insertOne(header);
        pagesRepo.insertOne(page);



//        Optional<Page> me = componentsRepo.findOne("view", "index.html");
//        var you = me.get();
//
        System.out.println("hello");

    }
}