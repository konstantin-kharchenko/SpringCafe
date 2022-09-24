package by.kharchenko.springcafe.model.dao;


import by.kharchenko.springcafe.model.entity.Product;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface ProductDao {
    List<Product> findNew();

    List<Product> findProductsByIdList(List<BigInteger> idList);

    List<Product> findByCurrentPage(Integer page);

    Long ordersCount();

    Optional<Product> findByName(String name);
}
