package com.bengodwinweb.pettycash.controller.api;

import com.bengodwinweb.pettycash.dto.model.UserDto;
import com.bengodwinweb.pettycash.dto.response.Response;
import com.bengodwinweb.pettycash.exception.NotFoundException;
import com.bengodwinweb.pettycash.exception.UnauthorizedException;
import com.bengodwinweb.pettycash.exception.ValidationException;
import com.bengodwinweb.pettycash.service.UserService;
import com.bengodwinweb.pettycash.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
public class UsersController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserUtil userUtil;

    @GetMapping("/users")
    public Response<Object> getAllUsers(Principal principal) throws UnauthorizedException, NotFoundException {
        UserDto currentUser = userService.getUserByEmail(principal.getName());
        if (!userUtil.isAdmin(currentUser.getEmail())) throw new UnauthorizedException("Not authorized to view users");

        List<UserDto> users = userService.getAllUsers();
        Response.PageMetaData meta = new Response.PageMetaData(users.size(), users.size(), 1, 1);
        return Response.ok().setPayload(users).setMetadata(meta);
    }

    @PutMapping("/users/{id}")
    public Response<Object> updateUser(@PathVariable("id") String id, @RequestBody @Valid UserDto userToUpdate, BindingResult result, WebRequest req, Errors errors, Principal principal) throws NotFoundException, UnauthorizedException, ValidationException {
        if (result.hasErrors()) throw new ValidationException("Not a valid User", errors);

        String principalId = userService.getIdByEmail(principal.getName());
        if (unauthorized(principal.getName(), principalId, id)) throw new UnauthorizedException("Not authorized to update User " + id);

        UserDto updated = userService.updateUser(userToUpdate);
        return Response.ok().setPayload(updated);
    }

    @GetMapping("/users/{id}")
    public Response<Object> getUser(@PathVariable("id") String id, Principal principal) throws NotFoundException, UnauthorizedException {
        String principalId = userService.getIdByEmail(principal.getName());
        if (unauthorized(principal.getName(), principalId, id)) throw new UnauthorizedException("Not authorized to view User " + id);

        UserDto user = userService.getUserById(id);
        return Response.ok().setPayload(user);
    }

    @DeleteMapping("/users/{id}")
    public Response<Object> deleteUser(@PathVariable("id") String id, Principal principal) throws NotFoundException, UnauthorizedException {
        String principalId = userService.getIdByEmail(principal.getName());
        if (unauthorized(principal.getName(), principalId, id)) throw new UnauthorizedException("Not authorized to delete User " + id);

        UserDto deletedUser = userService.deleteUserById(id);
        return Response.ok().setPayload(deletedUser);
    }


    private boolean unauthorized(String principalEmail, String principalId, String id) {
        return !userUtil.isAdmin(principalEmail) && !principalId.equals(id);
    }
}
