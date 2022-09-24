package by.kharchenko.springcafe.model.dao.impl;


import by.kharchenko.springcafe.model.dao.AdminDao;
import by.kharchenko.springcafe.model.dao.BaseDao;
import by.kharchenko.springcafe.model.entity.Administrator;
import by.kharchenko.springcafe.model.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Repository
public class AdminDaoImpl implements BaseDao<Administrator>, AdminDao {
    private static final Logger logger = LogManager.getLogger(AdminDaoImpl.class);

    @Override
    public boolean delete(BigInteger id) throws DaoException {
        return false;
    }

    @Override
    public boolean add(Administrator userData) throws DaoException {
        return false;
    }

    @Override
    public Optional<Administrator> findById(BigInteger id) throws DaoException {
        return null;
    }

    @Override
    public List<Administrator> findAll() throws DaoException {
        return null;
    }

    @Override
    public boolean update(Administrator t) throws DaoException {
        return false;
    }
}
