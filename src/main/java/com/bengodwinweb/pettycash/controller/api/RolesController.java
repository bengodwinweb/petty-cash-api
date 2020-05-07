package com.bengodwinweb.pettycash.controller.api;

import com.bengodwinweb.pettycash.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RolesController {

    @Autowired
    private RoleRepository roleRepository;
}
