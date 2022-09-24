package by.kharchenko.springcafe.validator.impl;

import by.kharchenko.springcafe.validator.Validator;
import org.springframework.stereotype.Component;

@Component
public class PhotoValidatorImpl implements Validator {

    private static final String PHOTO_REGEX = "^.+\\.(jpg|jpeg|raw|png)$";

    @Override
    public boolean isCorrect(String fileName){
        return fileName.matches(PHOTO_REGEX);
    }
}
