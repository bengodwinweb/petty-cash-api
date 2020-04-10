package com.bengodwinweb.pettycash.controller;

import com.bengodwinweb.pettycash.dto.response.Response;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Controller
public class RootController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public Response getRoot() {
        return new Response("Petty Cash status OK");
    }

    @RequestMapping(value = "/restricted", method = RequestMethod.GET)
    @ResponseBody
    public Response getCurrentUser(Principal principal) {
        return new Response("logged in as " + principal.getName());
    }
}
