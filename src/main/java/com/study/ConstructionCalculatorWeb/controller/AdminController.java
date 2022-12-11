package com.study.ConstructionCalculatorWeb.controller;

import com.study.ConstructionCalculatorWeb.entity.GroupOfUsers;
import com.study.ConstructionCalculatorWeb.entity.User;
import com.study.ConstructionCalculatorWeb.entity.UserStatus;
import com.study.ConstructionCalculatorWeb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/admin")
@RestController
public class AdminController {
    @Autowired
    private UserService userService;

    @PostMapping("/addUser")
    User addUser(@RequestBody User user){
        return userService.addUser(user);
    }

    @PostMapping("/updateUser")
    User updateUser(@RequestHeader Long id, User user){
        return userService.updateUser(id, user);
    }

    @GetMapping("/roles")
    List<GroupOfUsers> getRoles(){
        return userService.getRoles();
    }

    @GetMapping("/getStatuses")
    List<UserStatus> getStatuses(){
        return userService.getUserStatuses();
    }
}
