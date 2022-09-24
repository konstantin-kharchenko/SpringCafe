package by.kharchenko.springcafe.model.dao;


import by.kharchenko.springcafe.model.entity.Order;
import by.kharchenko.springcafe.model.entity.Product;
import by.kharchenko.springcafe.model.entity.User;

import java.math.BigInteger;
import java.util.List;
import java.util.Set;

public interface OrderDao {
    List<Order> findQuickToReceive(User user);

    Set<Product> findProductsByOrder(BigInteger idOrder);

    List<Order> findByCurrentPage(Integer page);

    Long ordersCount();
}
