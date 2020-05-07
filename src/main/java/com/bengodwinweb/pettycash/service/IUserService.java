package com.bengodwinweb.pettycash.service;

import com.bengodwinweb.pettycash.controller.request.UserSignupRequest;
import com.bengodwinweb.pettycash.dto.model.UserDto;
import com.bengodwinweb.pettycash.exception.EmailExistsException;
import com.bengodwinweb.pettycash.exception.NotFoundException;

import java.util.List;

public interface IUserService {

    UserDto signup(UserSignupRequest newUser) throws EmailExistsException;

    List<UserDto> getAllUsers();

    UserDto getUserById(String id) throws NotFoundException;

    String getIdByEmail(String email) throws NotFoundException;

    UserDto getUserByEmail(String email) throws NotFoundException;

    UserDto updateUser(UserDto userDto) throws NotFoundException;

    UserDto deleteUserById(String id) throws NotFoundException;

}
