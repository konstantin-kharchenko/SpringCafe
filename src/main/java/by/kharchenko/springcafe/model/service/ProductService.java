package by.kharchenko.springcafe.model.service;

import by.kharchenko.springcafe.model.entity.Product;
import by.kharchenko.springcafe.model.exception.ServiceException;

import java.math.BigInteger;
import java.util.List;

public interface ProductService extends BaseService<Product>{
    List<Product> findNew() throws ServiceException;

    List<Product> findProductsByIdList(List<BigInteger> idList) throws ServiceException;

    List<Product> findByCurrentPage(Integer page) throws ServiceException;

    Long ordersCount();

    Product findByName(String name);
}
