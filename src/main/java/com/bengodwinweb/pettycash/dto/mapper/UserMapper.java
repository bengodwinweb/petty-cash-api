package com.bengodwinweb.pettycash.dto.mapper;

import com.bengodwinweb.pettycash.dto.model.UserDto;
import com.bengodwinweb.pettycash.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public static UserDto toUserDto(User user) {
        return new UserDto()
                .setId(user.getId())
                .setEmail(user.getEmail())
                .setFirstName(user.getFirstName())
                .setLastName(user.getLastName());
    }
}
