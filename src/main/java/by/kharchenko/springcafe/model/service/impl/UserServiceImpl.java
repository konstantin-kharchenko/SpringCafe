package by.kharchenko.springcafe.model.service.impl;

import by.kharchenko.springcafe.model.dao.impl.UserDaoImpl;
import by.kharchenko.springcafe.model.entity.Role;
import by.kharchenko.springcafe.model.entity.User;
import by.kharchenko.springcafe.model.exception.DaoException;
import by.kharchenko.springcafe.model.exception.ServiceException;
import by.kharchenko.springcafe.model.service.UserService;
import by.kharchenko.springcafe.util.email.CustomMailSender;
import by.kharchenko.springcafe.util.encryption.EncryptionPassword;
import by.kharchenko.springcafe.util.filereadwrite.FileReaderWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);
    private static final String APP_MAIL = "cafe.from.app@mail.ru";
    private static final String REGISTRATION_HEAD_MAIL = "Registration message";
    private static final String REGISTRATION_TEXT_MAIL = "Congratulations on your successful registration in the cafe app";
    private final UserDaoImpl userDao;

    private final FileReaderWriter readerWriter;

    private final CustomMailSender mailSender;

    @Autowired
    public UserServiceImpl(UserDaoImpl userDao, FileReaderWriter readerWriter, CustomMailSender mailSender) {
        this.userDao = userDao;
        this.readerWriter = readerWriter;
        this.mailSender = mailSender;
    }

    @Override
    @Transactional
    public boolean delete(User user) throws ServiceException {
        return false;
    }

    @Override
    @Transactional
    public boolean delete(BigInteger id) throws ServiceException {
        return false;
    }

    @Override
    @Transactional
    public boolean add(User user) throws ServiceException {
        try {
            user.setRegistrationTime(new Date());
            boolean isLoginExists = userDao.idByLogin(user.getLogin()).isPresent();
            if (isLoginExists) {
                return false;
            }
            String encryptionPassword = EncryptionPassword.encryption(user.getPassword());
            user.setPassword(encryptionPassword);
            boolean match = userDao.add(user);
            if (match) {
                mailSender.sendCustomEmail(user.getEmail(), APP_MAIL, REGISTRATION_HEAD_MAIL, REGISTRATION_TEXT_MAIL);
            }
            return match;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional
    public User findById(BigInteger id) throws ServiceException {
        Optional<User> optionalUser;
        try {
            optionalUser = userDao.findById(id);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                if (user.getPhotoPath() != null) {
                    String stringPhoto = readerWriter.readPhoto(user.getPhotoPath());
                    user.setStringPhoto(stringPhoto);
                }
                return user;
            }
            return null;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional
    public List<User> findAll() throws ServiceException {
        return null;
    }

    @Override
    @Transactional
    public boolean update(User user) throws ServiceException {
        try {
            boolean isLoginExists = userDao.findAnotherIdByLogin(user.getLogin(), user.getIdUser()).isPresent();
            if (isLoginExists){
                return false;
            }
            return userDao.update(user);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional
    public Optional<Role> authenticate(String login, String password) throws ServiceException {
        String encryptionPassword = EncryptionPassword.encryption(password);
        boolean match = userDao.authenticate(login, encryptionPassword);
        if (match) {
            return userDao.findRoleByLogin(login);
        } else {
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public Optional<BigInteger> idByLogin(String login) {
        return userDao.idByLogin(login);
    }
}
