package by.kharchenko.springcafe.model.dao.impl;

import by.kharchenko.springcafe.model.dao.BaseDao;
import by.kharchenko.springcafe.model.dao.ClientDao;
import by.kharchenko.springcafe.model.entity.Client;
import by.kharchenko.springcafe.model.exception.DaoException;
import by.kharchenko.springcafe.model.exception.ServiceException;
import by.kharchenko.springcafe.util.encryption.CustomPictureEncoder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Repository
public class ClientDaoImpl implements BaseDao<Client>, ClientDao {
    private static final Logger logger = LogManager.getLogger(ClientDaoImpl.class);
    private final SessionFactory sessionFactory;

    @Autowired
    public ClientDaoImpl(@Qualifier(value = "getSessionFactory") SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public boolean delete(BigInteger id) throws DaoException {
        return false;
    }

    @Override
    public boolean add(Client userData) throws DaoException {
        return false;
    }

    @Override
    public Optional<Client> findById(BigInteger id) throws DaoException {
        return null;
    }

    @Override
    public List<Client> findAll() throws DaoException {
        return null;
    }

    @Override
    public boolean update(Client client) throws DaoException {
        Session session = sessionFactory.getCurrentSession();
        session.update(client);
        return true;
    }
}
