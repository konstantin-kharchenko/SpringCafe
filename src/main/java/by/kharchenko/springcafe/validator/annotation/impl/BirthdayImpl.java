package by.kharchenko.springcafe.validator.annotation.impl;

import by.kharchenko.springcafe.validator.annotation.Birthday;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

public class BirthdayImpl implements ConstraintValidator<Birthday, Date> {

    private int annotationYear;

    @Override
    public void initialize(Birthday birthday) {
        this.annotationYear = birthday.year();
    }

    @Override
    public boolean isValid(Date date, ConstraintValidatorContext constraintValidatorContext) {
        LocalDate localDate = Instant.ofEpochMilli(date.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        LocalDate currentDate = Instant.ofEpochMilli(new Date().getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        int year = Period.between(localDate, currentDate).getYears();

        if (annotationYear <= year) {
            return true;
        }
        return false;
    }
}