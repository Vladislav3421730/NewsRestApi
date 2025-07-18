package com.example.testtasknews.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = RolesValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Roles {
    String message() default "role must be in ['admin', 'journalist', 'subscriber']";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
