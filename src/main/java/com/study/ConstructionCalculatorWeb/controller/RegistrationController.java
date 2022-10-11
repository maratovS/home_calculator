package com.study.ConstructionCalculatorWeb.controller;

import com.study.ConstructionCalculatorWeb.Service.UserService;
import com.study.ConstructionCalculatorWeb.entity.GroupOfUsers;
import com.study.ConstructionCalculatorWeb.entity.User;
import com.study.ConstructionCalculatorWeb.repo.GroupOfUsersRepository;
import com.study.ConstructionCalculatorWeb.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RegistrationController {

    @Autowired
    private UserService userService;
    @Autowired
    private GroupOfUsersRepository groupOfUsersRepository;

    @GetMapping("/")
    List<User> users(){
        return userService.getUsers();
    }
}
