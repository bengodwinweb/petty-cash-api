package com.bengodwinweb.pettycash.controller.api;

import com.bengodwinweb.pettycash.dto.model.UserDto;
import com.bengodwinweb.pettycash.dto.response.Response;
import com.bengodwinweb.pettycash.exception.NotFoundException;
import com.bengodwinweb.pettycash.exception.UnauthorizedException;
import com.bengodwinweb.pettycash.service.UserService;
import com.bengodwinweb.pettycash.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
public class UsersController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserUtil userUtil;

    @GetMapping("/users")
    public Response getAllUsers(Principal principal) throws UnauthorizedException, NotFoundException {
        UserDto currentUser = userService.getUserByEmail(principal.getName());
        if (!userUtil.isAdmin(currentUser.getEmail())) throw new UnauthorizedException("Not authorized to view users");

        List<UserDto> users = userService.getAllUsers();

        Response.PageMetaData meta = new Response.PageMetaData(users.size(), users.size(), 1, 1);

        return Response.ok()
                .setPayload(users)
                .setMetadata(meta);
    }

    @GetMapping("/users/{id}")
    public Response getUser(@PathVariable("id") String id, Principal principal) throws NotFoundException, UnauthorizedException {
        UserDto currentUser = userService.getUserByEmail(principal.getName());
        if (!isAuthorized(currentUser, id)) throw new UnauthorizedException("Not authorized to view User " + id);

        UserDto user = userService.getUserById(id);

        return Response.ok().setPayload(user);
    }


    private boolean isAuthorized(UserDto currentUser, String id) {
        return userUtil.isAdmin(currentUser.getEmail()) || currentUser.getId().equals(id);
    }
}
