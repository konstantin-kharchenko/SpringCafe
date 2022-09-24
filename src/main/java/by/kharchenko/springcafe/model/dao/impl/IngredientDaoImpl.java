package by.kharchenko.springcafe.model.dao.impl;

import by.kharchenko.springcafe.model.dao.BaseDao;
import by.kharchenko.springcafe.model.dao.IngredientDao;
import by.kharchenko.springcafe.model.entity.Ingredient;
import by.kharchenko.springcafe.model.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import static by.kharchenko.springcafe.controller.DbColumn.ID_INGREDIENT;
import static by.kharchenko.springcafe.controller.DbColumn.ID_PRODUCT;

@Repository
public class IngredientDaoImpl implements BaseDao<Ingredient>, IngredientDao {
    private static final Logger logger = LogManager.getLogger(ProductDaoImpl.class);
    public static final String SELECT_GRAMS = "SELECT grams from products_ingredients where " +
            "id_product = :id_product AND id_ingredient = :id_ingredient";
    private final SessionFactory sessionFactory;

    @Autowired
    public IngredientDaoImpl(@Qualifier(value = "getSessionFactory") SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public boolean delete(BigInteger id) throws DaoException {
        return false;
    }

    @Override
    public boolean add(Ingredient ingredientData) throws DaoException {
        return false;
    }

    @Override
    public Optional<Ingredient> findById(BigInteger id) throws DaoException {
        return null;
    }

    @Override
    public List<Ingredient> findAll() throws DaoException {
        return null;
    }

    @Override
    public boolean update(Ingredient ingredientData) throws DaoException {
        return false;
    }

    @Override
    public void setGrams(BigInteger idProduct, List<Ingredient> ingredients) throws DaoException {
        Session session = sessionFactory.getCurrentSession();
        for (Ingredient ingredient: ingredients) {
            double grams = (Double)session.createSQLQuery(SELECT_GRAMS)
                    .setParameter(ID_PRODUCT, idProduct).setParameter(ID_INGREDIENT, ingredient.getIdIngredient()).getSingleResult();
            ingredient.setGrams(grams);
        }
    }
}
