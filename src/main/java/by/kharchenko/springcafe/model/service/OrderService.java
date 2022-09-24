package by.kharchenko.springcafe.model.service;

import by.kharchenko.springcafe.model.entity.Order;
import by.kharchenko.springcafe.model.entity.Product;
import by.kharchenko.springcafe.model.entity.User;
import by.kharchenko.springcafe.model.exception.ServiceException;

import java.math.BigInteger;
import java.util.List;
import java.util.Set;

public interface OrderService extends BaseService<Order>{
    List<Order> findQuickToReceive(User user) throws ServiceException;

    void addProductsInOrder(BigInteger id, Set<BigInteger> toList) throws ServiceException;

    void deleteProductFromOrderById(BigInteger idOrder, BigInteger idProduct) throws ServiceException;

    List<Order> findByCurrentPage(Integer page) throws ServiceException;

    Long ordersCount();
}
