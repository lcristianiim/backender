package org.interactor;

import org.interactor.moduleloader.ModulesLoader;

import java.util.List;

public class UsersService {
    UsersRepository repo = ModulesLoader.getUsersRepo();

    public List<User> getAllUsers() {
        return repo.getUsers();
    }

}
