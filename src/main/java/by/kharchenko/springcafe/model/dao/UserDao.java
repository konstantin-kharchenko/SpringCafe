package by.kharchenko.springcafe.model.dao;

import by.kharchenko.springcafe.model.entity.Role;
import by.kharchenko.springcafe.model.entity.User;

import java.math.BigInteger;
import java.util.Optional;

public interface UserDao {
    boolean authenticate(String login, String password);

    Optional<User> findByLogin(String login);

    Optional<Role> findRoleByLogin(String login);

    Optional<BigInteger> idByLogin(String login);

    void updatePhoto(String path, BigInteger id);

    Optional<BigInteger> findAnotherIdByLogin(String login, BigInteger idUser);

}
