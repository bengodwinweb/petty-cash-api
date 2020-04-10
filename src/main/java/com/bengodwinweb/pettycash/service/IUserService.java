package com.bengodwinweb.pettycash.service;

import com.bengodwinweb.pettycash.dto.model.NewUserDto;
import com.bengodwinweb.pettycash.exception.EmailExistsException;
import com.bengodwinweb.pettycash.model.User;

public interface IUserService {

    User signup(NewUserDto newUser) throws EmailExistsException;
}
