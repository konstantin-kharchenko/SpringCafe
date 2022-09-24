package by.kharchenko.springcafe.validator.annotation.impl;

import by.kharchenko.springcafe.validator.annotation.CustomFutureOrPresent;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Date;

public class CustomFutureOrPresentImpl implements ConstraintValidator<CustomFutureOrPresent, Date> {

    @Override
    public boolean isValid(Date date, ConstraintValidatorContext constraintValidatorContext) {
        Date date1 = new Date();
        if (date1.getTime() < date.getTime()){
            return true;
        }
        if (date1.getYear() == date.getYear() && date1.getMonth() == date.getMonth() &&date1.getDay() == date.getDay()){
            return true;
        }
        return false;
    }
}
