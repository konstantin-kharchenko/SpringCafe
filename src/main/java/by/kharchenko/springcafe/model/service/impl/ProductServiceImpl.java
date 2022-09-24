package by.kharchenko.springcafe.model.service.impl;

import by.kharchenko.springcafe.model.dao.impl.IngredientDaoImpl;
import by.kharchenko.springcafe.model.dao.impl.OrderDaoImpl;
import by.kharchenko.springcafe.model.dao.impl.ProductDaoImpl;
import by.kharchenko.springcafe.model.entity.Ingredient;
import by.kharchenko.springcafe.model.entity.Order;
import by.kharchenko.springcafe.model.entity.Product;
import by.kharchenko.springcafe.model.exception.DaoException;
import by.kharchenko.springcafe.model.exception.ServiceException;
import by.kharchenko.springcafe.model.service.BaseService;
import by.kharchenko.springcafe.model.service.ProductService;
import by.kharchenko.springcafe.util.filereadwrite.FileReaderWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductDaoImpl productDao;
    private final IngredientDaoImpl ingredientDao;
    private final FileReaderWriter readerWriter;

    @Autowired
    public ProductServiceImpl(ProductDaoImpl productDao, IngredientDaoImpl ingredientDao, FileReaderWriter readerWriter) {
        this.productDao = productDao;
        this.ingredientDao = ingredientDao;
        this.readerWriter = readerWriter;
    }

    @Override
    public boolean delete(Product product) throws ServiceException {
        return false;
    }

    @Override
    public boolean delete(BigInteger id) throws ServiceException {
        return false;
    }

    @Override
    public boolean add(Product userData) throws ServiceException {
        return false;
    }

    @Override
    @Transactional
    public Product findById(BigInteger id) throws ServiceException {
        try {
            Product product;
            Optional<Product> optionalProduct = productDao.findById(id);
            if (optionalProduct.isPresent()) {
                product = optionalProduct.get();
                ingredientDao.setGrams(product.getIdProduct(), product.getIngredients().stream().toList());
                product.setStringPhoto(readerWriter.readPhoto(product.getPhotoPath()));
                return product;
            }
            return null;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Product> findAll() throws ServiceException {
        return null;
    }

    @Override
    public boolean update(Product product) throws ServiceException {
        return false;
    }

    @Override
    @Transactional
    public List<Product> findNew() throws ServiceException {
        try {
            List<Product> products = productDao.findNew();
            for (Product product : products) {
                ingredientDao.setGrams(product.getIdProduct(), product.getIngredients().stream().toList());
                String stringPhoto = readerWriter.readPhoto(product.getPhotoPath());
                product.setStringPhoto(stringPhoto);
            }
            return products;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional
    public List<Product> findProductsByIdList(List<BigInteger> idList) throws ServiceException {
        try {
            List<Product> products = productDao.findProductsByIdList(idList);
            for (Product product : products) {
                ingredientDao.setGrams(product.getIdProduct(), product.getIngredients().stream().toList());
                product.setStringPhoto(readerWriter.readPhoto(product.getPhotoPath()));
            }
            return products;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional
    public List<Product> findByCurrentPage(Integer page) throws ServiceException {
        try {
            List<Product> products = productDao.findByCurrentPage(page);
            for (Product product : products) {
                ingredientDao.setGrams(product.getIdProduct(), product.getIngredients().stream().toList());
                String stringPhoto = readerWriter.readPhoto(product.getPhotoPath());
                product.setStringPhoto(stringPhoto);
            }
            return products;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional
    public Long ordersCount() {
        return productDao.ordersCount();
    }

    @Override
    @Transactional
    public Product findByName(String name) {
        Optional<Product> product = productDao.findByName(name);
        if (product.isPresent()) {
            return product.get();
        }
        return null;
    }
}
