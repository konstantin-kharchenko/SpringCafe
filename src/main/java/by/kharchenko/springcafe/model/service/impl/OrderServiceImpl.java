package by.kharchenko.springcafe.model.service.impl;

import by.kharchenko.springcafe.model.dao.impl.IngredientDaoImpl;
import by.kharchenko.springcafe.model.dao.impl.OrderDaoImpl;
import by.kharchenko.springcafe.model.dao.impl.ProductDaoImpl;
import by.kharchenko.springcafe.model.entity.Order;
import by.kharchenko.springcafe.model.entity.Product;
import by.kharchenko.springcafe.model.entity.User;
import by.kharchenko.springcafe.model.exception.DaoException;
import by.kharchenko.springcafe.model.exception.ServiceException;
import by.kharchenko.springcafe.model.service.OrderService;
import by.kharchenko.springcafe.util.filereadwrite.FileReaderWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderDaoImpl orderDao;
    private final IngredientDaoImpl ingredientDao;
    private final ProductDaoImpl productDao;
    private final FileReaderWriter readerWriter;

    @Autowired
    public OrderServiceImpl(OrderDaoImpl orderDao, IngredientDaoImpl ingredientDao, FileReaderWriter readerWriter, ProductDaoImpl productDao) {
        this.orderDao = orderDao;
        this.ingredientDao = ingredientDao;
        this.readerWriter = readerWriter;
        this.productDao = productDao;
    }


    @Override
    public boolean delete(Order order) throws ServiceException {
        return false;
    }

    @Override
    @Transactional
    public boolean delete(BigInteger id) throws ServiceException {
        try {
            return orderDao.delete(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional
    public boolean add(Order order) throws ServiceException {
        try {
            return orderDao.add(order);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional
    public Order findById(BigInteger id) throws ServiceException {
        try {
            Optional<Order> order = orderDao.findById(id);
            return order.orElse(null);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Order> findAll() throws ServiceException {
        return null;
    }

    @Override
    @Transactional
    public boolean update(Order order) throws ServiceException {
        try {
            return orderDao.update(order);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional
    public List<Order> findQuickToReceive(User user) throws ServiceException {
        try {
            List<Order> orders = orderDao.findQuickToReceive(user);
            for (Order order : orders) {
                List<Product> products = order.getProducts().stream().toList();
                for (Product product : products) {

                    ingredientDao.setGrams(product.getIdProduct(), product.getIngredients().stream().toList());

                    String stringPhoto = readerWriter.readPhoto(product.getPhotoPath());
                    product.setStringPhoto(stringPhoto);
                }
            }
            return orders;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional
    public void addProductsInOrder(BigInteger id, Set<BigInteger> toList) throws ServiceException {
        try {
            Order order;
            Optional<Order> optionalOrder = orderDao.findById(id);
            if (optionalOrder.isPresent()) {
                order = optionalOrder.get();
                Set<Product> oldProducts = order.getProducts();
                List<Product> products = productDao.findProductsByIdList(toList.stream().toList());
                oldProducts.addAll(products);
                BigDecimal sum = new BigDecimal("0");
                for (Product product : oldProducts) {
                    sum = sum.add(product.getPrice());
                }
                order.setPrice(sum);
                order.setProducts(oldProducts);
                orderDao.update(order);
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional
    public void deleteProductFromOrderById(BigInteger idOrder, BigInteger idProduct) throws ServiceException {
        try {
            Order order;
            Optional<Order> optionalOrder = orderDao.findById(idOrder);
            if (optionalOrder.isPresent()) {
                order = optionalOrder.get();
                order.getProducts().removeIf(o -> Objects.equals(o.getIdProduct(), idProduct));
                orderDao.update(order);
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional
    public List<Order> findByCurrentPage(Integer page) throws ServiceException {
        try {
            List<Order> orders = orderDao.findByCurrentPage(page);
            for (Order order : orders) {
                List<Product> products = order.getProducts().stream().toList();
                for (Product product : products) {
                    ingredientDao.setGrams(product.getIdProduct(), product.getIngredients().stream().toList());
                    String stringPhoto = readerWriter.readPhoto(product.getPhotoPath());
                    product.setStringPhoto(stringPhoto);
                }
            }
            return orders;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional
    public Long ordersCount() {
        return orderDao.ordersCount();
    }
}
