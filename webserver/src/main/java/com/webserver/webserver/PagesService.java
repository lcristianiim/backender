package com.webserver.webserver;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PagesService {

    @GetMapping("/getPage/{page}")
    public String getPage(@PathVariable String page) {
        if (page.equals("homepage")) {
            return "hello world";
        }
        return null;
    }
}
