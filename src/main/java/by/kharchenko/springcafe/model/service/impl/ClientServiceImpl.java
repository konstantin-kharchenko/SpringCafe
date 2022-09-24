package by.kharchenko.springcafe.model.service.impl;

import by.kharchenko.springcafe.model.dao.impl.ClientDaoImpl;
import by.kharchenko.springcafe.model.dao.impl.UserDaoImpl;
import by.kharchenko.springcafe.model.entity.Client;
import by.kharchenko.springcafe.model.entity.User;
import by.kharchenko.springcafe.model.exception.DaoException;
import by.kharchenko.springcafe.model.exception.ServiceException;
import by.kharchenko.springcafe.model.service.ClientService;
import by.kharchenko.springcafe.util.encryption.CustomPictureEncoder;
import by.kharchenko.springcafe.util.filereadwrite.FileReaderWriter;
import by.kharchenko.springcafe.validator.impl.PhotoValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {
    private static final Logger logger = LogManager.getLogger(ClientServiceImpl.class);
    public static final String PHOTO_PATH_ON_HDD = "D:\\FINAL_PROJECT\\PHOTO\\CLIENT\\";
    private static final String FILE_EXTENSION = ".txt";
    private final FileReaderWriter readerWriter;
    private final ClientDaoImpl clientDao;
    private final PhotoValidatorImpl photoValidator;
    private final UserDaoImpl userDao;

    @Autowired
    public ClientServiceImpl(FileReaderWriter readerWriter, ClientDaoImpl clientDao, UserDaoImpl userDao, PhotoValidatorImpl photoValidator) {
        this.readerWriter = readerWriter;
        this.clientDao = clientDao;
        this.userDao = userDao;
        this.photoValidator = photoValidator;
    }

    @Override
    public boolean delete(Client client) throws ServiceException {
        return false;
    }

    @Override
    public boolean delete(BigInteger id) throws ServiceException {
        return false;
    }

    @Override
    public boolean add(Client userData) throws ServiceException {
        return false;
    }

    @Override
    public Client findById(BigInteger id) throws ServiceException {
        return null;
    }

    @Override
    public List<Client> findAll() throws ServiceException {
        return null;
    }

    @Override
    public boolean update(Client client) throws ServiceException {
        return false;
    }

    @Override
    @Transactional
    public boolean updatePhoto(MultipartFile file, BigInteger id) throws ServiceException {
        try {
            String name = file.getOriginalFilename();
            if (name != null) {
                boolean isCorrect = photoValidator.isCorrect(name);
                if (isCorrect) {
                    byte[] photoBytes = file.getBytes();
                    String userPhoto = CustomPictureEncoder.arrayToBase64(photoBytes);
                    String path = PHOTO_PATH_ON_HDD + id + FILE_EXTENSION;
                    boolean isWrite = readerWriter.writePhoto(userPhoto, path);
                    if (isWrite) {
                        userDao.updatePhoto(path, id);
                        return true;
                    }
                    return false;
                }
                return false;
            }
            return false;
        } catch (IOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional
    public Client findByUserId(BigInteger id) throws ServiceException {
        try {
            var user = userDao.findById(id);
            return user.map(User::getClient).orElse(null);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional
    public boolean addClientAccount(BigDecimal clientAccount, BigInteger idUser) {
        try {
            if (clientAccount.compareTo(BigDecimal.ZERO) > 0) {
                Optional<User> optionalUser = userDao.findById(idUser);
                if (optionalUser.isPresent()) {
                    User user = optionalUser.get();
                    BigDecimal oldClientAccount = user.getClient().getClientAccount();
                    oldClientAccount = oldClientAccount.add(clientAccount);
                    user.getClient().setClientAccount(oldClientAccount);
                    clientDao.update(user.getClient());
                    return true;
                }
                return false;
            }
            return false;
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }

}
