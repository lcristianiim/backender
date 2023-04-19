package org.backender;

import org.backender.mongo.MongoRepo;
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
        MongoRepo repo = new MongoRepo(Component.class, "components");

        repo.insertOne(header);

//        Page page = new Page("Cool title", Theme.LIGHT, "test-a", "index.html", List.of(header));
//
//        MonogoRepo repo = new MonogoRepo(MongoPage.class, "pages");
//
//        Optional<Page> me = repo.findOne("view", "index.html");
//        var you = me.get();
//
        System.out.println("hello");

    }
}