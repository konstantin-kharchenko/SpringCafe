package by.kharchenko.springcafe.model.service;

import by.kharchenko.springcafe.model.entity.Client;
import by.kharchenko.springcafe.model.exception.ServiceException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import java.math.BigDecimal;
import java.math.BigInteger;

public interface ClientService extends BaseService<Client>{
    boolean updatePhoto(MultipartFile file, BigInteger id) throws ServiceException;

    Client findByUserId(BigInteger id) throws ServiceException;

    boolean addClientAccount(BigDecimal clientAccount, BigInteger idUser);
}
