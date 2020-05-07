package com.bengodwinweb.pettycash.dto.mapper;

import com.bengodwinweb.pettycash.dto.model.UserDto;
import com.bengodwinweb.pettycash.model.User;
import com.bengodwinweb.pettycash.util.DateUtil;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;


@Component
public class UserMapper {

    private static DateTimeFormatter formatter = DateUtil.formatter;

    public static UserDto toUserDto(User user) {
        return new UserDto()
                .setId(user.getId())
                .setEmail(user.getEmail())
                .setFirstName(user.getFirstName())
                .setLastName(user.getLastName())
                .setCreated(formatter.format(user.getCreated()))
                .setLastSeen(formatter.format(user.getLastSeen()));
    }
}
