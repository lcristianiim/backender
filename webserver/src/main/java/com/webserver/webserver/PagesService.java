package com.webserver.webserver;

import org.componenter.Page;
import org.componenter.Theme;
import org.componenter.components.Component;
import org.componenter.components.commons.Link;
import org.componenter.components.header.HeaderComponent;
import org.componenter.components.header.HeaderDS;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PagesService {

    @GetMapping("/getPage/{page}")
    public Page getPage(@PathVariable String page) {
        if (page.equals("homepage")) {

            HeaderDS data = new HeaderDS("Great Logo",
                    List.of(
                            new Link("First link", "page-one"),
                            new Link("Second link", "page-two")),
                    List.of(
                            new Link("Third link", "page-tree"),
                            new Link("Forth link", "page-four"))
            );


            Component header = new HeaderComponent(data);
            Page homepage = new Page("Cool title", Theme.LIGHT, "test-a", "index.html", List.of(header));
            return homepage;

        }
        return null;
    }
}
