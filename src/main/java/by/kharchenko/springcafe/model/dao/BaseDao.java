package by.kharchenko.springcafe.model.dao;


import by.kharchenko.springcafe.model.entity.AbstractEntity;
import by.kharchenko.springcafe.model.exception.DaoException;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;


public interface BaseDao<T extends AbstractEntity> {

    boolean delete(BigInteger id) throws DaoException;

    boolean add(T userData) throws DaoException;

    Optional<T> findById(BigInteger id) throws DaoException;

    List<T> findAll() throws DaoException;

    boolean update(T t) throws DaoException;

}