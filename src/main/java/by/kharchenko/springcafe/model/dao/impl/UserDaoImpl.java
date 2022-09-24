package by.kharchenko.springcafe.model.dao.impl;

import by.kharchenko.springcafe.model.dao.BaseDao;
import by.kharchenko.springcafe.model.dao.UserDao;
import by.kharchenko.springcafe.model.entity.Administrator;
import by.kharchenko.springcafe.model.entity.Client;
import by.kharchenko.springcafe.model.entity.Role;
import by.kharchenko.springcafe.model.entity.User;
import by.kharchenko.springcafe.model.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import static by.kharchenko.springcafe.controller.DbColumn.*;

@Repository
public class UserDaoImpl implements BaseDao<User>, UserDao {
    private static final Logger logger = LogManager.getLogger(UserDaoImpl.class);
    public static final String USER_BY_LOGIN = "from User u WHERE u.login LIKE :" + LOGIN;
    public static final String UPDATE_BY_ID = "update User SET login = :login, name = :name, surname = :surname, " +
            "phoneNumber = :phone_humber where idUser = :id_user";
    public static final String PASSWORD_BY_LOGIN = "SELECT password FROM User u WHERE u.login LIKE :login";
    public static final String ROLE_BY_LOGIN = "SELECT role FROM User u WHERE u.login LIKE :login";
    public static final String ID_BY_LOGIN = "SELECT idUser from User u WHERE u.login LIKE :login";
    public static final String UPDATE_PHOTO_PATH_BY_ID = "update User SET photoPath = :photo_path where idUser = :id_user";
    public static final String ID_BY_LOGIN_AND_NOT_ID = "SELECT idUser from User u WHERE u.login = :login " +
            "AND u.idUser != :id_user";

    private final SessionFactory sessionFactory;

    @Autowired
    public UserDaoImpl(@Qualifier(value = "getSessionFactory") SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public boolean delete(BigInteger id) throws DaoException {
        Session session = sessionFactory.getCurrentSession();
        User user = session.load(User.class, id);
        if (user != null) {
            session.delete(user);
            logger.info("User is removed: " + user);
        }
        return true;
    }

    @Override
    public boolean add(User user) throws DaoException {
        Session session = sessionFactory.getCurrentSession();
        session.persist(user);
        User newUser = (User) session.createQuery(USER_BY_LOGIN)
                .setParameter(LOGIN, user.getLogin()).getSingleResult();
        switch (user.getRole()) {
            case CLIENT -> {
                Client client = new Client();
                client.setUser(newUser);
                session.persist(client);
            }
            case ADMINISTRATOR -> {
                Administrator administrator = new Administrator();
                administrator.setUser(newUser);
                session.persist(administrator);
            }
        }
        logger.info("User is added: " + newUser);
        return true;
    }

    @Override
    public Optional<User> findById(BigInteger id) throws DaoException {
        Session session = sessionFactory.getCurrentSession();
        User user = session.get(User.class, id);
        return Optional.of(user);
    }

    @Override
    public List<User> findAll() throws DaoException {
        Session session = sessionFactory.getCurrentSession();
        return null;
    }

    @Override
    public boolean update(User user) throws DaoException {
        Session session = sessionFactory.getCurrentSession();
        session.createQuery(UPDATE_BY_ID).setParameter(LOGIN, user.getLogin())
                .setParameter(NAME, user.getName()).setParameter(SURNAME, user.getSurname())
                .setParameter(PHONE_NUMBER, user.getPhoneNumber())
                .setParameter(ID_USER, user.getIdUser()).executeUpdate();
        logger.info("User is updated: " + user);
        return true;
    }

    @Override
    public boolean authenticate(String login, String password) {
        Session session = sessionFactory.getCurrentSession();
        String password2 = (String) session.createQuery(PASSWORD_BY_LOGIN)
                .setParameter(LOGIN, login).getSingleResult();
        return password.equals(password2);
    }

    @Override
    public Optional<User> findByLogin(String login) {
        Session session = sessionFactory.getCurrentSession();
        User user = (User) session.createQuery(USER_BY_LOGIN)
                .setParameter(LOGIN, login).getSingleResult();
        if (user != null) {
            logger.info("User is find: " + user);
            return Optional.of(user);
        } else {
            logger.info("User isn't find: ");
            return Optional.empty();
        }
    }

    @Override
    public Optional<Role> findRoleByLogin(String login) {
        Session session = sessionFactory.getCurrentSession();
        Role role = (Role) session.createQuery(ROLE_BY_LOGIN)
                .setParameter(LOGIN, login).getSingleResult();
        if (role != null) {
            logger.info("Role: " + role + " is find");
            return Optional.of(role);
        } else {
            logger.info("role isn't find");
            return Optional.empty();
        }

    }

    @Override
    public Optional<BigInteger> idByLogin(String login) {
        try {
            Session session = sessionFactory.getCurrentSession();
            BigInteger id = (BigInteger) session.createQuery(ID_BY_LOGIN).setParameter(LOGIN, login).getSingleResult();
            logger.info("id: " + id + " by login: " + login);
            return Optional.of(id);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public void updatePhoto(String path, BigInteger id) {
        Session session = sessionFactory.getCurrentSession();
        session.createQuery(UPDATE_PHOTO_PATH_BY_ID).setParameter(PHOTO_PATH, path)
                .setParameter(ID_USER, id).executeUpdate();
    }

    @Override
    public Optional<BigInteger> findAnotherIdByLogin(String login, BigInteger idUser) {
        try {
            Session session = sessionFactory.getCurrentSession();
            BigInteger id = (BigInteger) session.createQuery(ID_BY_LOGIN_AND_NOT_ID)
                    .setParameter(LOGIN, login).setParameter(ID_USER, idUser).getSingleResult();
            return Optional.of(id);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
