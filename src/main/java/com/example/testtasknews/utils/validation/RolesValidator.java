package com.example.testtasknews.utils.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Set;

import static com.example.testtasknews.utils.Constants.*;

public class RolesValidator implements ConstraintValidator<RoleValidation,String> {

    @Override
    public boolean isValid(String role, ConstraintValidatorContext constraintValidatorContext) {


        if(role == null || role.isEmpty()) {
            return true;
        }

        Set<String> roleSet = Set.of(ADMIN, JOURNALIST, SUBSCRIBER);
        return roleSet.stream().anyMatch(x -> x.equals(role));

    }
}
