package org.backender.repositories;

import org.interactor.UsersRepository;
import org.interactor.moduleloader.RepoFactory;

public class UsersRepoFactory implements RepoFactory {
    @Override
    public UsersRepository getUsersRepository() {
        return new UsersRepo();
    }
}
