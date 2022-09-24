package by.kharchenko.springcafe.model.service;

import by.kharchenko.springcafe.model.entity.Role;
import by.kharchenko.springcafe.model.entity.User;
import by.kharchenko.springcafe.model.exception.ServiceException;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;
import java.util.Optional;

public interface UserService extends BaseService<User> {
    Optional<Role> authenticate(String login, String password) throws ServiceException;

    Optional<BigInteger> idByLogin(String login);
}
