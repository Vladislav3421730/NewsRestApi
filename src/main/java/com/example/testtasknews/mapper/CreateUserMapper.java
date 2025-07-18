package com.example.testtasknews.mapper;

import com.example.testtasknews.dto.request.CreateUserRequestDto;
import com.example.testtasknews.model.User;
import com.example.testtasknews.model.enums.Role;

import java.util.Set;
import static com.example.testtasknews.utils.Constants.*;

public class CreateUserMapper {

    public static User map(CreateUserRequestDto userRequestDto) {

        User user = User.builder()
                .name(userRequestDto.getName())
                .surname(userRequestDto.getSurname())
                .parentName(userRequestDto.getParentName())
                .username(userRequestDto.getUsername())
                .build();
        Role role = getRole(userRequestDto.getRole());
        if(role != null) {
            user.setRoleSet(Set.of(role));
        }
        return user;
    }

    private static Role getRole(String role) {
        if(role == null) return null;
        return switch (role) {
            case ADMIN -> Role.ROLE_ADMIN;
            case JOURNALIST -> Role.ROLE_JOURNALIST;
            case SUBSCRIBER -> Role.ROLE_SUBSCRIBER;
            default -> null;
        };
    }
}
