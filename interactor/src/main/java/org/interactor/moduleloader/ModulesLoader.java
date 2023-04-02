package org.interactor.moduleloader;

import org.interactor.UsersRepository;

import java.util.ServiceLoader;

public class ModulesLoader {

    public static UsersRepository getUsersRepo() {
        ServiceLoader<RepoFactory> loader = ServiceLoader.load(RepoFactory.class);
        return loader.stream().findFirst().orElseThrow().get().getUsersRepository();
    }
}
