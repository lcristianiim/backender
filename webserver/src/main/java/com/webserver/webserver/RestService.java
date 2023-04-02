package com.webserver.webserver;

import org.interactor.User;
import org.interactor.UsersService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RestService {

    @GetMapping("/")
    public List<User> getAllUsers() {
        UsersService service = new UsersService();
        return service.getAllUsers();
    }
}
