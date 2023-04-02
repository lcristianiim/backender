package org.backender.repositories;

import org.interactor.User;
import org.interactor.UsersRepository;

import java.util.List;
import java.util.stream.Collectors;

public class UsersRepo implements UsersRepository {
    @Override
    public List<User> getUsers() {
        List<UserEntity> usersFromDB = callDBForUsers();
        return mapUsersEntityToUser(usersFromDB);
    }

    private List<User> mapUsersEntityToUser(List<UserEntity> usersFromDB) {
        return usersFromDB.stream()
                .map(this::mapUserEntity)
                .collect(Collectors.toList());
    }

    private User mapUserEntity(UserEntity user) {
        return new User(user.getFirstName(), user.getLastName());
    }

    private List<UserEntity> callDBForUsers() {
        return List.of(new UserEntity(1, "John", "Wayne"));
    }
}
