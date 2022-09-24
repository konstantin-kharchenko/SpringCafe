package by.kharchenko.springcafe.model.dao.impl;

import by.kharchenko.springcafe.model.dao.BaseDao;
import by.kharchenko.springcafe.model.dao.OrderDao;
import by.kharchenko.springcafe.model.entity.Order;
import by.kharchenko.springcafe.model.entity.Product;
import by.kharchenko.springcafe.model.entity.User;
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
import java.util.Set;

import static by.kharchenko.springcafe.controller.DbColumn.CLIENT;

@Repository
public class OrderDaoImpl implements BaseDao<Order>, OrderDao {
    private static final Logger logger = LogManager.getLogger(OrderDaoImpl.class);
    public static final String ORDER_BY_CLIENT_ORDER_BY_DATE_OF_RECEIVING = "from Order o where " +
            "o.client = :client and o.received = false order by o.dateOfReceiving";
    public static final String ORDER_BY_PAGE = "from Order where received = false order by dateOfReceiving desc";
    public static final String COUNT = "select count(*) from Order";
    private final SessionFactory sessionFactory;

    @Autowired
    public OrderDaoImpl(@Qualifier(value = "getSessionFactory") SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public boolean delete(BigInteger id) throws DaoException {
        Session session = sessionFactory.getCurrentSession();
        Order order = session.get(Order.class, id);
        if (order != null) {
            session.delete(order);
            return true;
        }
        return false;
    }

    @Override
    public boolean add(Order order) throws DaoException {
        Session session = sessionFactory.getCurrentSession();
        session.persist(order);
        return true;
    }

    @Override
    public Optional<Order> findById(BigInteger id) throws DaoException {
        Session session = sessionFactory.getCurrentSession();
        Order order = session.get(Order.class, id);
        return Optional.of(order);
    }

    @Override
    public List<Order> findAll() throws DaoException {
        return null;
    }

    @Override
    public boolean update(Order orderData) throws DaoException {
        Session session = sessionFactory.getCurrentSession();
        Order order = session.get(Order.class, orderData.getIdOrder());
        orderData.setProducts(order.getProducts());
        orderData.setPrice(order.getPrice());
        session.clear();
        session.saveOrUpdate(orderData);
        return true;
    }

    @Override
    public List<Order> findQuickToReceive(User user) {
        Session session = sessionFactory.getCurrentSession();
        return (List<Order>) session.createQuery(ORDER_BY_CLIENT_ORDER_BY_DATE_OF_RECEIVING)
                .setFirstResult(0).setMaxResults(10).setParameter(CLIENT, user.getClient()).list();
    }

    @Override
    public Set<Product> findProductsByOrder(BigInteger idOrder) {
        Session session = sessionFactory.getCurrentSession();
        Order order = session.get(Order.class, idOrder);
        return order.getProducts();
    }

    @Override
    public List<Order> findByCurrentPage(Integer page) {
        Session session = sessionFactory.getCurrentSession();
        int offSet = (page - 1) * 10;
        return (List<Order>) session.createQuery(ORDER_BY_PAGE).setMaxResults(5).setFirstResult(offSet).list();
    }

    @Override
    public Long ordersCount() {
        Session session = sessionFactory.getCurrentSession();
        return (Long) session.createQuery(COUNT).uniqueResult();
    }
}
