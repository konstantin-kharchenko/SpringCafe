package by.kharchenko.springcafe.validator.annotation;

import by.kharchenko.springcafe.model.entity.AbstractEntity;
import by.kharchenko.springcafe.validator.annotation.impl.BirthdayImpl;

import javax.validation.Constraint;
import java.lang.annotation.*;

@Constraint(validatedBy = { BirthdayImpl.class })
@Documented
@Target({ ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.FIELD ,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Birthday {

    int year();

    String message() default "more";

    Class<?>[] groups() default {};
    Class<? extends AbstractEntity>[] payload() default {};
}
