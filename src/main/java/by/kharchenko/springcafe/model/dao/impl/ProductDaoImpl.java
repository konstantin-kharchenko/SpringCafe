package by.kharchenko.springcafe.model.dao.impl;

import by.kharchenko.springcafe.model.dao.BaseDao;
import by.kharchenko.springcafe.model.dao.ProductDao;
import by.kharchenko.springcafe.model.entity.Order;
import by.kharchenko.springcafe.model.entity.Product;
import by.kharchenko.springcafe.model.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import static by.kharchenko.springcafe.controller.DbColumn.NAME;
import static by.kharchenko.springcafe.controller.DbColumn.PRODUCT_IDS;

@Repository
public class ProductDaoImpl implements BaseDao<Product>, ProductDao {
    private static final Logger logger = LogManager.getLogger(ProductDaoImpl.class);
    public static final String PRODUCT_ORDER_BY_REGISTRATION_TIME = "from Product p order by p.registrationTime";
    public static final String PRODUCTS_BY_IDS = "SELECT * FROM products WHERE id_product IN :product_ids";
    public static final String PRODUCT_ORDER_BY_VALIDITY_DATE_DESC = "from Product order by validityDate desc";
    public static final String COUNT = "select count(*) from Product";
    public static final String PRODUCT_BY_NAME = "FROM Product WHERE name = :name";
    private final SessionFactory sessionFactory;

    @Autowired
    public ProductDaoImpl(@Qualifier(value = "getSessionFactory") SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public boolean delete(BigInteger id) throws DaoException {
      return false;
    }

    @Override
    public boolean add(Product productData) throws DaoException {
        return false;
    }

    @Override
    public Optional<Product> findById(BigInteger id) throws DaoException {
        Session session = sessionFactory.getCurrentSession();
        Product product = session.get(Product.class, id);
        return Optional.of(product);
    }

    @Override
    public List<Product> findAll() throws DaoException {
        return null;
    }

    @Override
    public boolean update(Product product) throws DaoException {
        return false;
    }

    @Override
    public List<Product> findNew() {
        Session session = sessionFactory.getCurrentSession();
        return (List<Product>) session.createQuery(PRODUCT_ORDER_BY_REGISTRATION_TIME).setFirstResult(0).setMaxResults(10).list();
    }

    @Override
    public List<Product> findProductsByIdList(List<BigInteger> idList) {
        Session session = sessionFactory.getCurrentSession();
        return (List<Product>) session.createSQLQuery(PRODUCTS_BY_IDS).setParameter(PRODUCT_IDS, idList).addEntity(Product.class).list();
    }

    @Override
    public List<Product> findByCurrentPage(Integer page) {
        Session session = sessionFactory.getCurrentSession();
        int offSet = (page - 1) * 10;
        return (List<Product>) session.createQuery(PRODUCT_ORDER_BY_VALIDITY_DATE_DESC).setMaxResults(5).setFirstResult(offSet).list();
    }

    @Override
    public Long ordersCount() {
        Session session = sessionFactory.getCurrentSession();
        return (Long) session.createQuery(COUNT).uniqueResult();
    }

    @Override
    public Optional<Product> findByName(String name) {
        try {
            Session session = sessionFactory.getCurrentSession();
            Product product = (Product) session.createQuery(PRODUCT_BY_NAME).setParameter(NAME, name).getSingleResult();
            return Optional.of(product);
        }catch (NoResultException e){
            return Optional.empty();
        }


    }
}
