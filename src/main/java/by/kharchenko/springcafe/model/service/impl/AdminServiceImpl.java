package by.kharchenko.springcafe.model.service.impl;
import by.kharchenko.springcafe.model.entity.Administrator;
import by.kharchenko.springcafe.model.exception.DaoException;
import by.kharchenko.springcafe.model.exception.ServiceException;
import by.kharchenko.springcafe.model.service.AdminService;
import by.kharchenko.springcafe.model.service.BaseService;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Override
    public boolean delete(Administrator administrator) throws ServiceException {
        return false;
    }

    @Override
    public boolean delete(BigInteger id) throws ServiceException {
        return false;
    }

    @Override
    public boolean add(Administrator userData) throws ServiceException {
        return false;
    }

    @Override
    public Administrator findById(BigInteger id) throws ServiceException {
        return null;
    }

    @Override
    public List<Administrator> findAll() throws ServiceException {
        return null;
    }

    @Override
    public boolean update(Administrator administrator) throws ServiceException {
        return false;
    }
}
