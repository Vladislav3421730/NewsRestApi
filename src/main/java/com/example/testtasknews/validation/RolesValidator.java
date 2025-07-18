package com.example.testtasknews.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.example.testtasknews.utils.Constants.*;

public class RolesValidator implements ConstraintValidator<Roles ,List<String>> {

    @Override
    public boolean isValid(List<String> roles, ConstraintValidatorContext constraintValidatorContext) {

        if(roles == null || roles.isEmpty()) {
            return true;
        }

        Set<String> allowedRoles = Set.of(ADMIN, JOURNALIST, SUBSCRIBER);

        boolean allValid = roles.stream()
                .allMatch(role -> allowedRoles.contains(role.toLowerCase()));

        return allValid && roles.size() == new HashSet<>(roles).size();
    }
}
