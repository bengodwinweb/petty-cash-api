package com.bengodwinweb.pettycash.controller.api;

import com.bengodwinweb.pettycash.dto.response.Response;
import com.bengodwinweb.pettycash.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Controller
public class RootController {

    @Autowired
    private UserUtil userUtil;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public Response getRoot() {
        return new Response("Petty Cash status OK");
    }

    @RequestMapping(value = "/restricted", method = RequestMethod.GET)
    @ResponseBody
    public Response getCurrentUser(Principal principal) {
        boolean isAdmin = userUtil.isAdmin(principal.getName());
        return new Response("logged in as " + principal.getName() + ", Admin: " + isAdmin);
    }
}
