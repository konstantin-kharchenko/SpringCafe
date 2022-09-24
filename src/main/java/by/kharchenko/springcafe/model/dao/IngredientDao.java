package by.kharchenko.springcafe.model.dao;


import by.kharchenko.springcafe.model.entity.Ingredient;
import by.kharchenko.springcafe.model.exception.DaoException;

import java.math.BigInteger;
import java.util.List;

public interface IngredientDao {
    void setGrams(BigInteger idProduct, List<Ingredient> ingredients) throws DaoException;
}
